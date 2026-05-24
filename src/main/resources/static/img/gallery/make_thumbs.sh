#!/bin/zsh

DIR="${1:-.}"
THUMBS="$DIR/thumbs"

mkdir -p "$THUMBS"

setopt nullglob

for f in "$DIR"/*.jpg "$DIR"/*.jpeg "$DIR"/*.JPG "$DIR"/*.JPEG "$DIR"/*.png "$DIR"/*.PNG; do
    filename=$(basename "$f")
    ffmpeg -i "$f" -vf "scale=200:-1" "$THUMBS/$filename" -y -loglevel error
    echo "Thumbed: $filename"
done
