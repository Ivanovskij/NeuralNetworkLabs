'use strict';

var signI = 
	'11111' +
	'00100' + 
	'00100' + 
	'11111'
;

var signV = 
	'10001' +
	'01010' + 
	'00100' + 
	'00100'
;

var signA = 
	'00100' +
	'01010' + 
	'11111' + 
	'10001'
;

var signN = 
	'10001' +
	'11001' + 
	'10101' + 
	'10001'
;

var listSigns = [ signI, signV, signA, signN ];
var lengthListSigns = listSigns.length - 1;

//  Порог функции активации
var bias = 4;

//  Инициализация весов сети нулем, 
// происходит автоматически (см порядок инициализации переменных)
// length 20, 5 * 4 - cells
var weights = new Array(20);
var lengthWeights = weights.length - 1;

function handle1(signOriginal, signToIndentify) {
	for (var i = 0; i <= lengthWeights; i++) {
		weights[i] = 0;
	}

	var inputSignPosition;
	if (signOriginal == 'I') {
		inputSignPosition = 0;
	} 
	else if (signOriginal == 'V') {
		inputSignPosition = 1;
	} 
	else if (signOriginal == 'A') {
		inputSignPosition = 2;
	} 
	else if (signOriginal == 'N') {
		inputSignPosition = 3;
	} else {
		alert('This word is not supported!');
		return;
	}

	training(inputSignPosition);

	// -- FOR debug
	var outSign = "";
	for (var i = 0; i < weights.length; i++) {
		if (i % 5 == 0) {
			outSign += '\r\n';
		}
		outSign += weights[i] + ' ';
	}

	alert(outSign);
	alert('This is ' + signOriginal + ' ' + proceed(signToIndentify, weights, bias));
}


// Тренировка сети
function training(inputSignPosition) {
	var globalError = 0;

	var outProceed = 0,
		tmpError = 0;

	var countIter = 0;
	var currentSign = listSigns[inputSignPosition];

	do {
		globalError = 0;

		for (var i = 0; i < currentSign.length; i++) {
			var option = getRandomInt(0, lengthListSigns);

			if (option != inputSignPosition) {
				outProceed = proceed(listSigns[option]);
				// правило хебба, 
				// вес либо увеличится, либо уменьшится
				tmpError = currentSign[i] - outProceed;
				globalError += Math.abs(tmpError);

				for (var j = 0; j <= lengthWeights; j++) {
					weights[j] += 0.1 * tmpError * currentSign[j];
				}
			}
		}

		countIter++;
	} while (globalError != 0);

	alert('Neuron trained for ' + countIter + ' iteration(s)!');
}

//	проверка на то, 
//	какую букву выдает сеть
function proceed(sign) {
	var net = 0;

	//	Рассчитываем взвешенную сумму
	for (var i = 0; i <= lengthWeights; i++) {
		net += sign[i] * weights[i];
	}
	//alert(net);

	//	Превышен ли порог? 
	// (Да - сеть думает, что это наша буква. 
	// Нет - сеть думает, что это другая буква)
	return net >= bias;
}

/**
 * Returns a random integer between min (inclusive) and max (inclusive)
 * Using Math.round() will give you a non-uniform distribution!
 */
function getRandomInt(min, max) {
	return Math.floor(Math.random() * (max - min + 1)) + min;
}