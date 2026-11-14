"""
Gera as fotografias de teste usadas por TestPictureDM e TestPictureHandler.

As fotografias originais (da máquina virtual da cadeira) já não existem, por isso
este script cria imagens JPG sintéticas com os mesmos nomes e com a data EXIF
(DateTimeOriginal) igual à data que aparece no nome de cada ficheiro.

Uso:  python scripts/gerar_fotos_teste.py   (requer Pillow: pip install pillow)
"""
from pathlib import Path
from PIL import Image, ImageDraw

DESTINO = Path(__file__).resolve().parent.parent / "fotos/FotosParaImportar"

# (pasta, ficheiro, data EXIF, cor base)
FOTOS = [
    ("AnaLibano",    "P_20201226_145438.jpg",              "2020:12:26 14:54:38", (220, 80, 60)),
    ("AnaLibano",    "P_20200618_194511.jpg",              "2020:06:18 19:45:11", (240, 170, 40)),
    ("AnaLibano",    "IMG_20160301_160428113_HDR.jpg",     "2016:03:01 16:04:28", (60, 140, 220)),
    ("AnaLibano",    "IMG_20160711_163640895.jpg",         "2016:07:11 16:36:40", (60, 180, 110)),
    ("DanielSantos", "IMG_20190626_091207.jpg",            "2019:06:26 09:12:07", (150, 90, 200)),
]

EXIF_IFD = 0x8769
DATETIME_ORIGINAL = 0x9003


def gerar(caminho: Path, data: str, cor: tuple) -> None:
    largura, altura = 640, 480
    img = Image.new("RGB", (largura, altura))
    draw = ImageDraw.Draw(img)
    # Gradiente vertical a partir da cor base
    for y in range(altura):
        f = 0.4 + 0.6 * (1 - y / altura)
        draw.line([(0, y), (largura, y)], fill=tuple(int(c * f) for c in cor))
    draw.text((20, 20), f"{caminho.name}\n{data}", fill=(255, 255, 255))

    exif = Image.Exif()
    exif.get_ifd(EXIF_IFD)[DATETIME_ORIGINAL] = data
    caminho.parent.mkdir(parents=True, exist_ok=True)
    img.save(caminho, "JPEG", quality=85, exif=exif)


if __name__ == "__main__":
    for pasta, nome, data, cor in FOTOS:
        caminho = DESTINO / pasta / nome
        gerar(caminho, data, cor)
        print(f"criado {caminho.relative_to(DESTINO)}  ({data})")
