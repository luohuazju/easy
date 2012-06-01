var urlHolder = new Object();

function loadTable() {
	jQuery.get(urlHolder.records, function(response) {
		jQuery('#tableUsers').find('tbody').remove();
 		for (var i=0; i<response.users.length; i++) {
			var row = '<tr>';
			row += '<td><input type="radio" name="index" id="index" value="'+i+'"></td>';
			row += '<td>' + response.users[i].username + '</td>';
			row += '<td>' + response.users[i].firstName + '</td>';
			row += '<td>' + response.users[i].lastName + '</td>';
			row += '<td>' + getRole(response.users[i].role.role) + '</td>';
			row += '</tr>';
	 		jQuery('#tableUsers').append(row);
 		}
 		
 		jQuery('#tableUsers').data('model', response.users);
		toggleForms('hide'); ;
 	});
}

function submitNewRecord() {
	jQuery.post(urlHolder.add, {
			username: jQuery('#newUsername').val(),
			password: jQuery('#newPassword').val(),
			firstName: jQuery('#newFirstName').val(),
			lastName: jQuery('#newLastName').val(),
			role: jQuery('#newRole').val()
		}, 
		function(response) {
			if (response != null) {
				loadTable();
				toggleForms('hide'); ;
				toggleCrudButtons('show');
				alert('Success! Record has been added.');
			} else {
				alert('Failure! An error has occurred!');
			}
		}
	);	
}

function submitDeleteRecord() {
	var selected = jQuery('input:radio[name=index]:checked').val();
	
	jQuery.post(urlHolder.del, {
			username: jQuery('#tableUsers').data('model')[selected].username
		}, 
		function(response) {
			if (response == true) {
				loadTable(urlHolder.records);
				alert('Success! Record has been deleted.');
			} else {
				alert('Failure! An error has occurred!');
			}
		}
	);
}

function submitUpdateRecord() {
	jQuery.post(urlHolder.edit, {
			userName: jQuery('#editUsername').val(),
			firstName: jQuery('#editFirstName').val(),
			lastName: jQuery('#editLastName').val(),
			role: jQuery('#editRole').val()
		}, 
		function(response) {
			if (response != null) {
				loadTable();
				toggleForms('hide'); ;
				toggleCrudButtons('show');
				alert('Success! Record has been edited.');
			} else {
				alert('Failure! An error has occurred!');
			}
		}
	);
}

function getRole(role) {
	if (role == 1) {
		return 'Admin';
	} else if (role == 2) {
		return 'Regular';
	} else {
		return 'Unknown';
	}
}

function hasSelected() {
	var selected = jQuery('input:radio[name=index]:checked').val();
	if (selected == undefined) { 
		alert('Select a record first!');
		return false;
	}
	
	return true;
}

function fillEditForm() {
	var selected = jQuery('input:radio[name=index]:checked').val();
	jQuery('#editUsername').val( jQuery('#tableUsers').data('model')[selected].username );
	jQuery('#editFirstName').val( jQuery('#tableUsers').data('model')[selected].firstName );
	jQuery('#editLastName').val( jQuery('#tableUsers').data('model')[selected].lastName );
	jQuery('#editRole').val( jQuery('#tableUsers').data('model')[selected].role.role );
}

function resetNewForm() {
	jQuery('#newUsername').val('');
	jQuery('#newPassword').val('');
	jQuery('#newFirstName').val('');
	jQuery('#newLastName').val('');
	jQuery('#newRole').val('2');
}

function resetEditForm() {
	jQuery('#editFirstName').val('');
	jQuery('#editLastName').val('');
	jQuery('#editRole').val('2');
}

function toggleForms(id) {
	if (id == 'hide') {
		jQuery('#newForm').hide();
		jQuery('#editForm').hide();
		
	} else if (id == 'new') {
 		resetNewForm();
 		jQuery('#newForm').show();
 		jQuery('#editForm').hide();
 		
	} else if (id == 'edit') {
 		resetEditForm();
 		jQuery('#newForm').hide();
 		jQuery('#editForm').show();
	}
}

function toggleCrudButtons(id) {
	if (id == 'show') {
		jQuery('#newBtn').removeAttr('disabled');
		jQuery('#editBtn').removeAttr('disabled');
		jQuery('#deleteBtn').removeAttr('disabled');
		jQuery('#reloadBtn').removeAttr('disabled');
		
	} else if (id == 'hide'){
		jQuery('#newBtn').attr('disabled', 'disabled');
		jQuery('#editBtn').attr('disabled', 'disabled');
		jQuery('#deleteBtn').attr('disabled', 'disabled');
		jQuery('#reloadBtn').attr('disabled', 'disabled');
	}
}
