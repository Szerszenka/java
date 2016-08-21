function setProjectId(select){
    var optionValue = select.value;
    document.getElementById('project_id').value = optionValue;
    if (document.getElementById('name').value.length > 1) {
        document.getElementById('add').disabled = false;
    }
}

function setTaskId(select) {
    var optionValue = select.value;
    document.getElementById('task_id').value = optionValue;
}

function setStatus(select) {
    var optionValue = select.value;
    document.getElementById('status').value = optionValue;
}
function setExecutorId(select) {
    var optionValue = select.value;
    document.getElementById('executor_id').value = optionValue;
}
function setExecutorIdField(select) {
    var optionValue = select.value;
    document.getElementById('executor_id').value = optionValue;
}

function popupform(myform, windowname) {
    if (!window.focus) {
        return true;
    }
    window.open('', windowname, 'height=700,width=600,scrollbars=yes,left=570,top=130');
    myform.target = windowname;
    return true;
}

function getRowIndex(x) {
    document.getElementById('oldIndex').value = document.getElementById('hiddenRowIndex').value;
    document.getElementById('hiddenRowIndex').value = x.rowIndex;
    var name = "executor" + document.getElementById('oldIndex').value;
    var selection = document.getElementById(name);
    if(document.getElementById('oldIndex').value != x.rowIndex ) {
        for(var i = 0; i < selection.length; i++){
            selection[i].selected = false;
        }
        // clear executors_id when user changed row
        document.getElementById('executor_id').value = '';
    } 
    //var strUser = selection.options[selection.selectedIndex].value;
   // document.getElementById('executor_id').value = strUser;
    var optionVal = new Array();
    var nameExecutor = "executor" + document.getElementById('hiddenRowIndex').value;
    var x = document.getElementById(nameExecutor); 
    var optionVal = new Array();
    for (i = 0; i < x.length; i++) { 
        if(x.options[i].selected) {
            optionVal.push(x.options[i].value);
        }
    }
    document.getElementById('executor_id').value = optionVal;
    var idOfTask = document.getElementById('example').rows[parseInt(document.getElementById('hiddenRowIndex').value)].cells[1].textContent;
    document.getElementById('tasks').value = idOfTask;
    document.getElementById('idtask').value = idOfTask;
}

function checkbox(selection) {
    document.getElementById('assign').disabled = !selection.checked;
    document.getElementById('edit').disabled = !selection.checked;

    if(document.getElementById('executor_id').value != null && document.getElementById('executor_id').value != '' 
            && document.getElementById('executor_id').value != ' ' && document.getElementById('executor_id').value.trim() != "None") {
        document.getElementById('remove').disabled = !selection.checked;
    } else {
        document.getElementById('remove').disabled = selection.checked;
    }
}

function disableButton() {
    var name = "checkbox" + document.getElementById('hiddenRowIndex').value;
    var box = document.getElementById(name);
    if(box.checked && document.getElementById('executor_id').value != null && document.getElementById('executor_id').value != '' 
            && document.getElementById('executor_id').value != ' ') {
        document.getElementById('checkbox_index').value = name;
        document.getElementById('remove').disabled = box.checked.checked;
    } 
}

function disableButtonChange() {
    var name = "checkbox" + document.getElementById('hiddenRowIndex').value;
    var box = document.getElementById(name);
    if(box.checked) {
        document.getElementById('remove').disabled = box.checked.checked;
    }
    
}

function formValidation(oEvent) {
    oEvent = oEvent || window.event;
    var valid = true;
    var patternHours = /^(\s*|\d+)$/;
    var patternName = /^[^\\/?%*:|<>\.]+$/;
    var patternDate = /^$|^(((((1[26]|2[048])00)|[12]\d([2468][048]|[13579][26]|0[48]))-((((0[13578]|1[02])-(0[1-9]|[12]\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\d|30)))|(02-(0[1-9]|[12]\d))))|((([12]\d([02468][1235679]|[13579][01345789]))|((1[1345789]|2[1235679])00))-((((0[13578]|1[02])-(0[1-9]|[12]\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\d|30)))|(02-(0[1-9]|1\d|2[0-8])))))$/;
    if (!patternName.test(document.getElementById('name').value) || document.getElementById('name').value.length < 1
            || document.getElementById('project_id').value.length < 1) {
        valid = false;
    } 
    if (!patternHours.test(document.getElementById('work_hours').value)) {
        valid = false;
    } 
    if (!patternDate.test(document.getElementById('start_date').value) || !patternDate.test(document.getElementById('end_date').value)) {
        valid = false;
    } 
    if (valid) {
        document.getElementById('add').disabled = false;
    } else {
        document.getElementById('add').disabled = true;
    }
    
}

window.onload = function () {
    var textName = document.getElementById('name');
    var textHours = document.getElementById('work_hours');
    var textSDate = document.getElementById('start_date');
    var textEDate = document.getElementById('end_date');
    textName.onchange = formValidation;
    textHours.onchange = formValidation;
    textSDate.onchange = formValidation;
    textEDate.onchange = formValidation;
    textName.onkeyup = formValidation;
    textHours.onkeyup = formValidation;
    textSDate.onkeyup = formValidation;
    textEDate.onkeyup = formValidation;
};