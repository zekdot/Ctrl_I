$(document).ready(function() {
  const video = document.getElementById('webcam');
  const overlay = document.getElementById('overlay');

  window.facetracker = {
    video: video,
    videoWidthExternal: video.width,
    videoHeightExternal: video.height,
    videoWidthInternal: video.videoWidth,
    videoHeightInternal: video.videoHeight,
    overlay: overlay,
    overlayCC: overlay.getContext('2d'),

    trackingStarted: false,
    currentPosition: null,
    currentEyeRect: null,
    isDetect : true,

    adjustVideoProportions: function() {
      // resize overlay and video if proportions of video are not 4:3
      // keep same height, just change width
      facetracker.videoWidthInternal = video.videoWidth;
      facetracker.videoHeightInternal = video.videoHeight;
      const proportion =
        facetracker.videoWidthInternal / facetracker.videoHeightInternal;
      facetracker.videoWidthExternal = Math.round(
        facetracker.videoHeightExternal * proportion,
      );
      facetracker.video.width = facetracker.videoWidthExternal;
      facetracker.overlay.width = facetracker.videoWidthExternal;
    },

    gumSuccess: function(stream) {
      //ui.onWebcamEnabled();
      //console.log('stream is ' + stream)
      console.log('摄像头成功启动');
      // add camera stream if getUserMedia succeeded
      if ('srcObject' in facetracker.video) {
        facetracker.video.srcObject = stream;
      } else {
        facetracker.video.src =
          window.URL && window.URL.createObjectURL(stream);
      }

      facetracker.video.onloadedmetadata = function() {
        facetracker.adjustVideoProportions();
        facetracker.video.play();
      };

      facetracker.video.onresize = function() {
        facetracker.adjustVideoProportions();
        if (facetracker.trackingStarted) {
          facetracker.ctrack.stop();
          facetracker.ctrack.reset();
          facetracker.ctrack.start(facetracker.video);
        }
      };
    },

    gumFail: function() {
      ui.showInfo(
        'There was some problem trying to fetch video from your webcam 😭',
        true,
      );
    },

    startVideo: function() {
      // start video
      facetracker.video.play();
      // start tracking
      facetracker.ctrack.start(facetracker.video);
      facetracker.trackingStarted = true;
      // start loop to draw face
      facetracker.positionLoop();
    },

    positionLoop: function() {
      // Check if a face is detected, and if so, track it.
      requestAnimationFrame(facetracker.positionLoop);
      facetracker.currentPosition = facetracker.ctrack.getCurrentPosition();
      facetracker.overlayCC.clearRect(
        0,
        0,
        facetracker.videoWidthExternal,
        facetracker.videoHeightExternal,
      );
      if (facetracker.currentPosition) {
        facetracker.isDetect = true;
        facetracker.trackFace(facetracker.currentPosition);
        facetracker.ctrack.draw(facetracker.overlay);
        //ui.onFoundFace();
      }else{
        facetracker.isDetect =false;
      }
    },

    getEyesRect: function(position) {
      // Given a tracked face, returns a rectangle surrounding the eyes.
      const minX = position[19][0] + 3;
      const maxX = position[15][0] - 3;
      const minY =
        Math.min(
          position[20][1],
          position[21][1],
          position[17][1],
          position[16][1],
        ) + 6;
      const maxY =
        Math.max(
          position[23][1],
          position[26][1],
          position[31][1],
          position[28][1],
        ) + 3;

      const width = maxX - minX;
      const height = maxY - minY - 5;
      return [minX, minY, width, height * 1.25];
    },

    getFaceRect: function(position) {
        const minX=Math.min(position[19][0],position[44][0])-5;
        const maxX=Math.max(position[15][0],position[50][0])+5;
        const minY=Math.min(position[20][1],position[21][1],position[17][1],position[16][1])-5;
        const maxY=Math.max(position[54][1],position[53][1],position[52][1])+5;
        const width = maxX - minX;
        const height = maxY - minY;

      return [minX, minY, width, height];
    },
    
    trackFace: function(position) {
      // Given a tracked face, crops out the eyes and draws them in the eyes canvas.
      const rect = facetracker.getEyesRect(position);
      facetracker.currentEyeRect = rect;
      
      const faceRect = facetracker.getFaceRect(position);
      
      const eyesCanvas = document.getElementById('eyes');
      
      const eyesCtx = eyesCanvas.getContext('2d');
      const faceCanvas= document.getElementById('face');
      
      const faceCtx = faceCanvas.getContext('2d');

      // Resize because the underlying video might be a different resolution:
      const resizeFactorX =
        facetracker.videoWidthInternal / facetracker.videoWidthExternal;
      const resizeFactorY =
        facetracker.videoHeightInternal / facetracker.videoHeightExternal;

      facetracker.overlayCC.strokeStyle = 'red';
      facetracker.overlayCC.strokeRect(rect[0], rect[1], rect[2], rect[3]);
      facetracker.overlayCC.strokeStyle = 'blue';
      facetracker.overlayCC.strokeRect(faceRect[0], faceRect[1], faceRect[2],faceRect[3]);
      
      eyesCtx.drawImage(
        facetracker.video,
        rect[0] * resizeFactorX,
        rect[1] * resizeFactorY,
        rect[2] * resizeFactorX,
        rect[3] * resizeFactorY,
        0,
        0,
        eyesCanvas.width,
        eyesCanvas.height,
      );
      faceCtx.drawImage(
        facetracker.video,
        faceRect[0] * resizeFactorX,
        faceRect[1] * resizeFactorY,
        faceRect[2] * resizeFactorX,
        faceRect[3] * resizeFactorY,
        0,
        0,
        faceCanvas.width,
        faceCanvas.height,
      );
      adjustImageGamma(eyesCanvas);
      adjustImageGamma(faceCanvas);
    },
  };

  video.addEventListener('canplay', facetracker.startVideo, false);

  // set up video
  if (navigator.mediaDevices) {
    navigator.mediaDevices
      .getUserMedia({
        video: true,
      })
      .then(facetracker.gumSuccess)
      .catch(facetracker.gumFail);
  } else if (navigator.getUserMedia) {
    navigator.getUserMedia(
      {
        video: true,
      },
      facetracker.gumSuccess,
      facetracker.gumFail,
    );
  } else {
    //TODO 提示用户摄像头无法打开
  }

  facetracker.ctrack = new clm.tracker();
  facetracker.ctrack.init();
});

function getGammaVal(imageData) {
  var pixData = imageData.data;
  var grayNum = pixData.length / 4;
  var totalGrayVal = 0;
  for (var i = 0; i < pixData.length; i += 4) {
    /* RGB to Luma: 
     * http://stackoverflow.com/questions/37159358/save-canvas-in-grayscale */
    //var grayscale = pix[i] * 0.2126 + pix[i+1] * 0.7152 + pix[i+2] * 0.0722;
    var grayscale = (pixData[i] + pixData[i+1] + pixData[i+2]) / 3;
    totalGrayVal = totalGrayVal + grayscale;
  }
  var mean = totalGrayVal / grayNum;
  var gammaVal = Math.log10(mean/255) / Math.log10(0.5);
  return gammaVal;
}

/**
 * 修正图片gamma值
 *
 */
function adjustImageGamma(canvas) {

  //canvas.id = img.id;
  //canvas.className = img.className;
  //canvas.width = img.naturalWidth;
  //canvas.width = img.width;
  //canvas.height = img.naturalHeight;
  //canvas.height = img.height;
  
  var ctx = canvas.getContext('2d');
  //ctx.drawImage(img, 0, 0);
  //ctx.drawImage(img, 0, 0, img.width, img.height);
  
  //var image_parentNode = img.parentNode;
  //image_parentNode.replaceChild(canvas, img);
    
  //imageData = ctx.getImageData(0, 0, img.naturalWidth, img.naturalHeight);
  var imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);
  
  var gammaVal = getGammaVal(imageData);
  var gammaCorrection = 1 / gammaVal;
  
  //setTimeout(function() {
    //for ( y = 0; y < image.naturalHeight; y++) {
    for ( y = 0; y < canvas.height; y++) {
      //for ( x = 0; x < image.naturalWidth; x++) {
      for ( x = 0; x < canvas.width; x++) {
        var index = parseInt(x + canvas.width * y) * 4;
        resetPixelColor(imageData,index,gammaCorrection);
      }
    }
    ctx.putImageData(imageData, 0, 0);
    //var dataURL = canvas.toDataURL('image/png');
  //}, 0);
}
 
function resetPixelColor(imageData,index,gammaCorrection) {
  imageData.data[index + 0] = Math.pow(
    (imageData.data[index + 0] / 255), gammaCorrection) * 255;
  imageData.data[index + 1] = Math.pow(
    (imageData.data[index + 1] / 255), gammaCorrection) * 255;
  imageData.data[index + 2] = Math.pow(
    (imageData.data[index + 2] / 255), gammaCorrection) * 255;
}