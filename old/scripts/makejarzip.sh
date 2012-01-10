#!/bin/bash

#file=`ls ~/Dropbox/jKaiUI/jkaiui_source_ver.0.5.0/dist/*.jar`
sourcefolder=$1
file=`ls ${sourcefolder}/dist/*.jar`
basename=${file##*/}
filename=${basename%.*}
#jarfolder=${file%$basename}dist/
#sourcefolder=${jarfolder%dist/}

file2=${sourcefolder}/LICENSE
folder=~/Dropbox/jKaiUI/${filename}

mkdir $folder

cp $file $folder
cp $file2 $folder

mkdir ${folder}/sound
mkdir ${folder}/log
mkdir ${folder}/setting
mkdir ${folder}/emoticons

cd ~/Dropbox/jKaiUI/
zip -r ${filename}.zip ${filename}

rm -rf ${filename}