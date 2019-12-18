function readFile() {
    if (this.files && this.files[0]) {
        var FR = new FileReader();
        FR.addEventListener("load", function (e) {
            document.getElementById("imgPreview").src = e.target.result;
            document.getElementById("imgBase64").value = e.target.result;
        });
        FR.readAsDataURL(this.files[0]);
    }
}
document.getElementById("inputImg").addEventListener("change", readFile);