

//计算文件md5
function Md5File(file, fileId, callback) {
 console.time('get md5: ' + file.name);
 handle_file_select(file, fileId, function(md5) {
  console.timeEnd('get md5: ' + file.name);
  setTimeout(function() {
   callback(md5);
  }, 1);
 });
}

//计算block MD5
function md5Block(blob, callback){
 //console.time('get block md5: ' );
 handle_file_select(blob, "fileId", function(chunkMd5) {
  //console.timeEnd('get block md5: ' );
  setTimeout(function() {
   callback(chunkMd5);
  }, 1);
 });
}

/**
 * compute md5 
 */

$(document).ready(function() {
	if (( typeof File !== 'undefined') && !File.prototype.slice) {
		if (File.prototype.webkitSlice) {
			File.prototype.slice = File.prototype.webkitSlice;
		}

		if (File.prototype.mozSlice) {
			File.prototype.slice = File.prototype.mozSlice;
		}
	}

	if (!window.File || !window.FileReader || !window.FileList || !window.Blob || !File.prototype.slice) {
		alert('File APIs are not fully supported in this browser. Please use latest Mozilla Firefox or Google Chrome.');
	}

});

// var file_id = 1;

function hash_file(file, workers) {
	var i, buffer_size, block, threads, reader, blob, handle_hash_block, handle_load_block;

	handle_load_block = function(event) {
		for ( i = 0; i < workers.length; i += 1) {
			threads += 1;
			workers[i].postMessage({
				'message' : event.target.result,
				'block' : block
			});
		}
	};
	handle_hash_block = function(event) {
		threads -= 1;

		if (threads === 0) {
			if (block.end !== file.size) {
				block.start += buffer_size;
				block.end += buffer_size;

				if (block.end > file.size) {
					block.end = file.size;
				}
				reader = new FileReader();
				reader.onload = handle_load_block;
				blob = file.slice(block.start, block.end);

				reader.readAsArrayBuffer(blob);
			}
		}
	};
	buffer_size = 64 * 16 * 1024;
	block = {
		'file_size' : file.size,
		'start' : 0
	};

	block.end = buffer_size > file.size ? file.size : buffer_size;
	threads = 0;

	for ( i = 0; i < workers.length; i += 1) {
		workers[i].addEventListener('message', handle_hash_block);
	}
	reader = new FileReader();
	reader.onload = handle_load_block;
	blob = file.slice(block.start, block.end);

	reader.readAsArrayBuffer(blob);
}

function handle_file_select(file, fileId, callback) {

	var  worker = new Worker(baseUrl + '/plugins/fileCalculator/calculator.worker.md5.js');
	var workers = [];
	// output = [];
	
	worker.onmessage = function(event) {
		$percent = $('#' + fileId).find('.progress .progress-bar-info');
		if (event.data.result) {
			$('#uploadProgress').width("100%")
		    $('#uploadProgress span').html("MD5: 100%");			
			// console.info("md5  " + event.data.result);
		} else {
			var percent = parseInt(event.data.block.end * 100 / event.data.block.file_size) + '%';
			$('#uploadProgress').width(percent)
		    $('#uploadProgress span').html("MD5: "+percent);	
			//console.info(fileId + " " + percent);
		}
		if (event.data.result != "" && event.data.result != null) {
			callback(event.data.result);

		}

	};

	// worker.addEventListener('message', handle_worker_event(fileId));
	workers.push(worker);

	hash_file(file, workers);
	// file_id += 1;

}