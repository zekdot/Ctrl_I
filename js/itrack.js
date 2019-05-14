window.itrack = {
  currentModel: null,
  inTraining: false,
  epochsTrained: 0,
  getEyesImage: function() {
    // Capture the current image in the eyes canvas as a tensor.
    return tf.tidy(function() {
      const image = tf.browser.fromPixels(document.getElementById('eyes'));
      const batchedImage = image.expandDims(0);
      return batchedImage
        .toFloat()
        .div(tf.scalar(127))
        .sub(tf.scalar(1));
    });
  },

  //获取脸部图像并转化为张量
  getFaceImage: function() {
    return tf.tidy(function() {
      const image = tf.browser.fromPixels(document.getElementById('face'));
      const batchedImage = image.expandDims(0);
      return batchedImage
        .toFloat()
        .div(tf.scalar(127))
        .sub(tf.scalar(1));
    });
  },

  getMetaInfos: function(mirror) {
    // Get some meta info about the rectangle as a tensor:
    // - middle x, y of the eye rectangle, relative to video size
    // - size of eye rectangle, relative to video size
    // - angle of rectangle (TODO)
    let x = facetracker.currentEyeRect[0] + facetracker.currentEyeRect[2] / 2;
    let y = facetracker.currentEyeRect[1] + facetracker.currentEyeRect[3] / 2;

    x = (x / facetracker.videoWidthExternal) * 2 - 1;
    y = (y / facetracker.videoHeightExternal) * 2 - 1;

    const rectWidth =
      facetracker.currentEyeRect[2] / facetracker.videoWidthExternal;
    const rectHeight =
      facetracker.currentEyeRect[3] / facetracker.videoHeightExternal;

    if (mirror) {
      x = 1 - x;
      y = 1 - y;
    }
    return tf.tidy(function() {
      return tf.tensor1d([x, y, rectWidth, rectHeight]).expandDims(0);
    });
  },
  getPrediction: function() {
    // Return relative x, y where we expect the user to look right now.
    return tf.tidy(function() {
      let eye = itrack.getEyesImage();
      let face = itrack.getFaceImage();
      //img = dataset.convertImage(img);
      //img.print()
      //return;
      const metaInfos = itrack.getMetaInfos();
      
      const cs1=tf.tensor(1.0)
      const cs2=tf.tensor(1.0)
      const cs3=tf.tensor(1.0)
      const prediction = itrack.currentModel.predict({'x0':eye,'x_face':face,'x_info':metaInfos,'keep_prob_eye':cs1,'keep_prob_face':cs2,'keep_prob':cs3});
     
     array=prediction.arraySync()
     //console.log(array)翻页翻页
     res=[array[0][0] + 0.5, array[0][1] + 0.5];
     //prediction.print()
     //console.log(res)
     return res;
    });
  },
  loadModel:async function(){
      itrack.currentModel=await tf.loadGraphModel('./model.json')
      console.log('执行了加载函数')
  }
};
