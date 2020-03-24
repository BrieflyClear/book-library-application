var dropDownList = document.getElementById("category.select");
dropDownList.addEventListener("change", dropDownListChanged);

function dropDownListChanged(name) {
    var selected = dropDownList.value;
 
    window.location.href = "/?category=" + selected.replace(/ /g, "+").replace(/&/g, "%26");
}