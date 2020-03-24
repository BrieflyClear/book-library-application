
function dropDownListChanged() {
    var selected = dropDownList.value;
    window.location.href = "/?category=" + selected.replace(/ /g, "+").replace(/&/g, "%26");
}

function showBookDetails(isbn) {
    window.location.href = "/book?id=" + isbn;
}