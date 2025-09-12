// File size validation for profile image upload
document.addEventListener('DOMContentLoaded', function() {
    const fileInput = document.getElementById("profileImage");
    const submitBtn = document.getElementById("submit2");
    const fileError = document.getElementById("fileError");

    if (fileInput && submitBtn && fileError) {
        fileInput.addEventListener("change", function () {
            if (this.files.length > 0) {
                const file = this.files[0];
                if (file.size > 1048576) { // 1 MB in bytes
                    fileError.style.display = "block";
                    submitBtn.disabled = true;
                } else {
                    fileError.style.display = "none";
                    submitBtn.disabled = false;
                }
            }
        });
    }
});