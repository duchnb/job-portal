// File size validation for profile image and resume uploads (max 1 MB)
document.addEventListener('DOMContentLoaded', function() {
    const MAX_SIZE = 1048576; // 1 MB in bytes

    // Elements (may or may not exist depending on the page)
    const submitBtn = document.getElementById('submit2');

    const imgInput = document.getElementById('profileImage');
    const imgError = document.getElementById('fileError');

    const resumeInput = document.getElementById('profileResume');
    const resumeError = document.getElementById('resumeError');

    // Track validity of each field present on the page
    let imgValid = true;    // assume it valid until a file is selected
    let resumeValid = true; // same as above

    function updateSubmitState() {
        if (!submitBtn) return; // nothing to disable
        //  submit if any present input is invalid
        const anyInvalid = (!imgValid && !!imgInput) || (!resumeValid && !!resumeInput);
        submitBtn.disabled = anyInvalid;
    }

    function validateFile(inputEl, errorEl, setValid) {
        if (!inputEl || !errorEl) return;
        inputEl.addEventListener('change', function() {
            // If user cleared the selection
            if (this.files.length === 0) {
                errorEl.style.display = 'none';
                setValid(true);
                updateSubmitState();
                return;
            }
            const file = this.files[0];
            if (file.size > MAX_SIZE) {
                errorEl.style.display = 'block';
                setValid(false);
            } else {
                errorEl.style.display = 'none';
                setValid(true);
            }
            updateSubmitState();
        });
    }

    // Initialize listeners for whichever inputs exist on the page
    if (imgInput && imgError) {
        validateFile(imgInput, imgError, (v) => { imgValid = v; });
    }
    if (resumeInput && resumeError) {
        validateFile(resumeInput, resumeError, (v) => { resumeValid = v; });
    }

    // Initial state check (in case page loads with preselected files via browser autofill)
    function initialCheck(inputEl, errorEl, setValid) {
        if (!inputEl || !errorEl) return;
        if (inputEl.files && inputEl.files.length > 0) {
            const file = inputEl.files[0];
            const ok = file.size <= MAX_SIZE;
            errorEl.style.display = ok ? 'none' : 'block';
            setValid(ok);
        }
    }

    initialCheck(imgInput, imgError, (v) => { imgValid = v; });
    initialCheck(resumeInput, resumeError, (v) => { resumeValid = v; });
    updateSubmitState();
});