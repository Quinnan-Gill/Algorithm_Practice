#!/bin/bash

echo COMPRESSING

for f in example_files/*; do
	f=${f##*/} 
	echo PROCESSING $f
    mkdir example_files/${f}_compressions
    cp example_files/${f} example_files/${f}_compressions/original_${f}
    echo myLZW - n
    java MyLZW - n < example_files/${f} > example_files/${f}_compressions/myLZW_n_${f}
    echo myLZW - r
    java MyLZW - r < example_files/${f} > example_files/${f}_compressions/myLZW_r_${f}
    echo myLZW - m
    java MyLZW - m < example_files/${f} > example_files/${f}_compressions/myLZW_m_${f}
    echo LZW
    java LZW - < example_files/${f} > example_files/${f}_compressions/LZW_${f}
    echo gzip
    gzip -k example_files/${f}
    mv example_files/${f}.gz example_files/${f}_compressions/gzip_${f}
done

echo DONE COMPRESSING
echo LOGGING

for d in example_files/*/; do
	echo LOGGING FROM $d
	for f in $d/*; do
		f=${f##*/} 
		echo LOGGING $d$f
		echo FILE: $f >> $d/log.txt
		FILESIZE=$(wc -c < $d/$f)
		echo Filesize of $f: $FILESIZE >> $d/log.txt
	done
done

echo DONE LOGGING

echo DONE PROCESSING
