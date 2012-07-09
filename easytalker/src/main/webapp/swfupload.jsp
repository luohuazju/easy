<html>
	<head>
		<title>SWFUpload Demo</title>
<link rel="stylesheet" type="text/css" href="./resources/component/swfupload/2.2.0.1/default.css">
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript" src="./resources/component/swfupload/2.2.0.1/swfupload.js"></script>
<script type="text/javascript" src="./resources/component/swfupload/2.2.0.1/swfupload.queue.js"></script>
<script type="text/javascript" src="./resources/component/swfupload/2.2.0.1/fileprogress.js"></script>
<script type="text/javascript" src="./resources/component/swfupload/2.2.0.1/handlers.js"></script>
<script type="text/javascript">
var swfupload;
$(function(){
	var settings = {
			flash_url : "./resources/component/swfupload/2.2.0.1/swfupload.swf",
			flash9_url : "./resources/component/swfupload/2.2.0.1/swfupload_fp9.swf",
			upload_url: "swfupload.do",
			file_size_limit : "100 MB",
			file_types : "*.*",
			file_types_description : "All Files",
			file_upload_limit : 100,
			file_queue_limit : 0,
			custom_settings : {
				cancelButtonId : "btnCancel",
				progressTarget : "fsUploadProgress"
			},
			debug: false,

			// Button settings
			button_image_url: "./resources/component/swfupload/2.2.0.1/XPButtonUploadText_61x22.png",
			button_width: "80",
			button_height: "22",
			button_placeholder_id: "spanButtonPlaceHolder",
			button_text: '<span class="theFont">Add File</span>',
			button_text_style: ".theFont { font-size: 12; }",
			button_text_left_padding: 4,
			button_text_top_padding: 3,
			
			// The event handler functions are defined in handlers.js
			swfupload_preload_handler : preLoad,
			swfupload_load_failed_handler : loadFailed,
			file_queued_handler : fileQueued,
			file_queue_error_handler : fileQueueError,//select file error
			file_dialog_complete_handler : fileDialogComplete,//after selecting file
			upload_start_handler : uploadStart,
			upload_progress_handler : uploadProgress,
			upload_error_handler : uploadError,//upload fail
			upload_success_handler : uploadSuccess,//upload success
			upload_complete_handler : uploadComplete,//file upload complete
			queue_complete_handler : queueComplete	// Queue plugin event
		};
	swfupload = new SWFUpload(settings);
});
</script>
	</head>
	<body>
		<div><span id="attachment">file:</span></div>
		<div>
			<span id="spanButtonPlaceHolder"></span>
			<input id="btnCancel" type="button" value="Cancel" onclick="swfupload.cancelQueue();" disabled="disabled"/>
			<span id="divStatus">files uploaded</span>
		</div>
		<div id="fsUploadProgress"></div>
	</body>
</html>