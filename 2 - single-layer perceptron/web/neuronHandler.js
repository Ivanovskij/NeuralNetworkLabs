
$("#button").on("click", function() {

	'use strict';

	selectingOneDigits();

	// composite sign
	var signToIndentify = "";
	$("#table td input").each(function () {
		if ($(this).val() == '') {
			signToIndentify += '0';
		} else {
			signToIndentify += '1';
		}
	});


	var signOriginal = $('#train-sign').val();
	// identify sign
	handle1(signOriginal, signToIndentify);

});


function selectingOneDigits() {
	/**************************************************
	** if in input.val() == 1, make background black
	**************************************************/
	var digitOne = 0;
	$("#table td input").each(function () {
		digitOne = $(this).val();
		if (digitOne != 0) {
			$(this).css('background', '#000'); 
		}
	});
}
