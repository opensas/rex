function crudOrderBy(newOrder) {
	crudSetControlText('#orden', newOrder, true);
}

function crudSelect(row, id) {
	crudSetControlText('#id', id);
	$('table.crudBrowseRows table').removeClass('crudRowSelected');
	$(row).addClass('crudRowSelected'); 
}

function crudPage(page) {
	crudSetControlText('#pagina', page, true);
}

function crudPaginado(control) {
	var paginado = $(control).val();
	crudSetControlText('#pagina', 1, false);
	crudSetControlText('#paginado', paginado, true);
}
function crudPaginaSelect(control) {
	var pagina = $(control).val();
	if (isNumber(pagina)) crudPage(pagina);
}

function crudSetControlText(controlSelector, text, submit) {
	var orderControl = $(controlSelector);
	if (orderControl) orderControl.val(text);
	
	if (submit) {
		var form = $('form');
		if (form) form.submit();
	}
}

function changeImageOnHover(selector) {
	$(selector).hover(
		function() {
			var img = $(this).find("img")[0];
			img.src = img.src.replace(".", "Hover.");
		},
		function() {
			var img = $(this).find("img")[0];
			img.src = img.src.replace("Hover.", ".");
		}
	);
}

function isNumber(n) {
  return !isNaN(parseFloat(n)) && isFinite(n);
}
		
		