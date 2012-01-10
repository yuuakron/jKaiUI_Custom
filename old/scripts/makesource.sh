#!/bin/bash

#file=`ls ~/Dropbox/jKaiUI/jkaiui_source_ver.0.5.0/dist/*.jar`
sourcefolder=$1
foldername=${sourcefolder##*/}
array=(`ls $1`)
echo ${array[@]}

array1=${array[@]/LICENSE/ }
array2=${array1[@]/build.xml/ }
array3=${array2[@]/info/ }
array4=${array3[@]/jsmooth/ }
array5=${array4[@]/lib/ }
array6=${array5[@]/manifest.mf/ }
array7=${array6[@]/nbproject/ }
array8=${array7[@]/resources/ }
array9=${array8[@]/src/ }


#echo ${array9[@]}

#cp -r ${sourcefolder} ${sourcefolder}-backup

cd ${sourcefolder}
rm -rf ${array9[@]}

cd ..
zip -r ${foldername}.zip ${foldername}