
<html>
<head><title>FFMPEG video processor</title>

</head>
<body>
<form action="" method="post">
<label>Background brightness: </label><input type="text" name="brightness" value="0.3"> from -1.0 to 1.0<br>
<label>Background contract: </label><input type="text" name="contrast" value="0.7">  from 0.0 to 2.0<br>
<label>Overlay transparency: </label><input type="text" name="alpha" value="0.8"> from 0.0 to 1.0<br>
<input value="submit" type="submit" name="submit">
</form>

<?php
if(isset($_POST['submit'])){
$brightness = isset($_POST['brightness'])?$_POST['brightness']:0.3;
$contrast = isset($_POST['contrast'])?$_POST['contrast']:0.7;
$alpha = isset($_POST['alpha'])?$_POST['alpha']:0.8;

$path = getcwd();
echo "Starting ffmpeg...<br>";
echo "Listing all videos...<br>";
$input_videos =  glob("input/*.{mp4,mov,mpeg}", GLOB_BRACE);
shuffle($input_videos );

$tmp_files = glob("tmp/*.*");
foreach($tmp_files as $tmp){
unlink($tmp);
}
$video_count = 1;
$videos_list =  '';
foreach($input_videos as $video){
echo "Converting ".$path.'/'.$video."...<br>";
$cmd =  'ffmpeg -i '.$path.'/'.$video.' -target pal-dvd  -aspect 4:3 '.$path.'/tmp/'.$video_count.'.mpeg';
	
	
	 shell_exec($cmd);
	 $videos_list .= "file '".$path."/tmp/".$video_count.".mpeg'\n";
	 $video_count++;

}
file_put_contents('tmp/videos_list.txt', $videos_list);
echo "Merging Videos...<br>";
$cmd =  'ffmpeg -auto_convert 1 -f concat -i '.$path.'/tmp/videos_list.txt -c copy -aspect 4:3 '.$path.'/tmp/overlay.mpeg';
shell_exec($cmd);
$cmd =  "ffmpeg -i ".$path."/tmp/overlay.mpeg 2>&1 | grep \"Duration\"| cut -d ' ' -f 4 | sed s/,// | sed 's@\..*@@g' | awk '{ split($1, A, \":\"); split(A[3], B, \".\"); print 3600*A[1] + 60*A[2] + B[1] }'";
$overlay_duration = shell_exec($cmd);
echo "Listing all images...<br>";
$input_images =  glob("images/*.{jpg,png}", GLOB_BRACE);
shuffle($input_images );
$images_list = '';
$images_list .= "file '".$path."/".array_values($input_images)[0]."'\n";
$images_list .= "duration 1\n";
foreach($input_images as $image){

$images_list .= "file '".$path."/".$image."'\n";
$images_list .= "duration ".$overlay_duration/count($input_images)."\n";
}
$images_list .= "file '".$path."/".array_values($input_images)[0]."'\n";
$images_list .= "duration 1\n";
file_put_contents('tmp/images_list.txt', $images_list);
echo "Converting images to slideshow...<br>";
$cmd = 'ffmpeg -f concat -i '.$path.'/tmp/images_list.txt -target pal-dvd  -aspect 4:3 '.$path.'/tmp/slideshow.mpeg';

shell_exec($cmd);
echo "Overlaying Videos...<br>";
$final_video = time();
$cmd = 'ffmpeg -i '.$path.'/tmp/slideshow.mpeg  -i '.$path.'/tmp/overlay.mpeg -filter_complex " \
        [0:v]setpts=PTS-STARTPTS, scale=780x660,eq=brightness='.$brightness.':contrast='.$contrast.'[bottom]; \
        [1:v]setpts=PTS-STARTPTS, scale=480x360,format=yuva420p,colorchannelmixer=aa='.$alpha.' \
             [top]; \
        [bottom][top]overlay=(main_w-overlay_w)/2:(main_h-overlay_h)/2"       '.$path.'/output/'.$final_video.'.mp4';
shell_exec($cmd);

echo 'Done<br>';
echo 'Please allow few seconds then download the file from here:<a href="output/'.$final_video.'.mp4">'.$final_video.'.mp4</a>';
}
die();

    	echo "Starting ffmpeg...\n\n";
	//echo shell_exec("ffmpeg -i /home/ffmpeg/public_html/input/1.mp4  /home/ffmpeg/public_html/output/1.mp4 &");
	
	$cmd =  'ffmpeg -i slideshow.mpeg  -i overlay.mpeg -filter_complex " \
        [0:v]setpts=PTS-STARTPTS, scale=780x660,eq=brightness=0.3:contrast=0.7[bottom]; \
        [1:v]setpts=PTS-STARTPTS, scale=480x360,format=yuva420p,colorchannelmixer=aa=0.8 \
             [top]; \
        [bottom][top]overlay=(main_w-overlay_w)/2:(main_h-overlay_h)/2"       out2.mp4; php /home/ffmpeg/public_html/steps/step1.php';
	
	//exec('bash -c "exec nohup setsid '.$cmd.' > /dev/null 2>&1 &"');
	 shell_exec($cmd);
	
	echo "Done.\n";
	
	?>
	
	</body>
</html>