const $target = $('#target');
const targetSize = $target.outerWidth();
function moveTarget() {
  // Move the model target to where we predict the user is looking to
  if (itrack.currentModel == null || facetracker.currentEyeRect==null || itrack.inTraining) {
    return;
  }
  const prediction = itrack.getPrediction();
  //console.log(prediction)
  const left = prediction[0] * ($('body').width() - $('#target').outerWidth());
  const top = prediction[1] * ($('body').height() - $('#target').outerWidth());
  //console.log(left)
  //console.log(right)
  $('#target').css('left', left + 'px');
  $('#target').css('top', top + 'px');
}