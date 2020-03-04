function MascaraCPF(campo, e) {
	if ((e.which >= 48) && (e.which <= 57)) {
		if (campo.value.length == 3 || campo.value.length == 7) {
			campo.value += '.';
		} else if (campo.value.length == 11) {
			campo.value += '-';
		}
		return true;
	} else if (e.which == 0 || e.which == 8 || e.which == 127){
		return true;
	} else {
		return false;
	}
}
		
function MascaraData(campo, e) {
	if (e.which == 0 || e.which == 8 || e.which == 127){
		return true;
	} else if ((campo.value.length == 2 || campo.value.length == 5) && e.which != 47) {
		campo.value += '/';
	} else if (e.which == 47) {
		return false;
	} else if (campo.value.length == 10) {
		return false;
	}
	return true;
}

