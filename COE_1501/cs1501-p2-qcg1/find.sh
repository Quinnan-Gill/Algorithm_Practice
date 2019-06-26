#!/bin/bash

javac MyLZW.java
javac LZW.java

for file in $(ls files/)
do
    echo $file "-" $(python file_size.py $(echo "files"/$file)) bytes

    echo "-------------------------------------------"
    echo "  Compression Type |    Size      |  Ratio "

    java LZW - < files/$file > tmp.lzw
    echo -n "  Normal LZW:      |" $(python file_size.py tmp.lzw) bytes" |"
    echo "    "$(python file_size.py files/$file tmp.lzw)

    java MyLZW - n < files/$file > tmp.lzw
    echo -n "  Do Nothing Mode: |" $(python file_size.py tmp.lzw) bytes" |"
    echo "    "$(python file_size.py files/$file tmp.lzw)
    java MyLZW + < tmp.lzw > out.ans

    if ! cmp -s "files/$file" out.ans; then
        echo "Invalid compression"
        exit 1
    fi

    java MyLZW - r < files/$file > tmp.lzw
    echo -n "  Reset Mode:      |" $(python file_size.py tmp.lzw) bytes" |"
    echo "    "$(python file_size.py files/$file tmp.lzw)
    java MyLZW + < tmp.lzw > out.ans

    if ! cmp -s "files/$file" out.ans; then
        echo "Invalid compression"
        exit 1
    fi

    java MyLZW - m < files/$file > tmp.lzw
    echo -n "  Monitor Mode:    |" $(python file_size.py tmp.lzw) bytes" |"
    echo "    "$(python file_size.py files/$file tmp.lzw)
    java MyLZW + < tmp.lzw > out.ans

    if ! cmp -s "files/$file" out.ans; then
        echo "Invalid compression"
        exit 1
    fi

    rm tmp.lzw
    rm out.ans

    cp files/$file .
    gzip $file

    echo -n "  GZIP:            |" $(python file_size.py $file.gz) bytes" |"
    echo "    "$(python file_size.py files/$file $file.gz)

    rm $file.gz

    echo ""
done
