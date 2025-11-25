// File size and filename length validation for uploads
// - Max size: 1 MB
// - Max filename length (DB limit for profile photos): 64 characters
// Note: Resume filename is typically fine (DB default 255), so length check applies to image only.
document.addEventListener('DOMContentLoaded', function() {
    const MAX_SIZE = 1048576; // 1 MB in bytes
    const MAX_NAME_LEN = 64; // DB column limit for profilePhoto

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
        // disable submit if any present input is invalid
        const anyInvalid = (!imgValid && !!imgInput) || (!resumeValid && !!resumeInput);
        submitBtn.disabled = anyInvalid;
    }

    function validateImage(inputEl, errorEl, setValid) {
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
            let errorMsg = '';
            if (file.size > MAX_SIZE) {
                errorMsg = 'File size exceeds 1 MB. Please choose a smaller image.';
            } else if (file.name && file.name.length > MAX_NAME_LEN) {
                errorMsg = 'File name is too long (' + file.name.length + ' chars). Max allowed is ' + MAX_NAME_LEN + ' characters.';
            }

            if (errorMsg) {
                errorEl.textContent = errorMsg;
                errorEl.style.display = 'block';
                setValid(false);
            } else {
                errorEl.style.display = 'none';
                setValid(true);
            }
            updateSubmitState();
        });
    }

    function validateResume(inputEl, errorEl, setValid) {
        if (!inputEl || !errorEl) return;
        inputEl.addEventListener('change', function() {
            if (this.files.length === 0) {
                errorEl.style.display = 'none';
                setValid(true);
                updateSubmitState();
                return;
            }
            const file = this.files[0];
            const tooBig = file.size > MAX_SIZE;
            if (tooBig) {
                errorEl.textContent = 'File size exceeds 1 MB. Please choose a smaller resume file.';
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
        validateImage(imgInput, imgError, (v) => { imgValid = v; });
    }
    if (resumeInput && resumeError) {
        validateResume(resumeInput, resumeError, (v) => { resumeValid = v; });
    }

    // Initial state check (in case page loads with preselected files via browser autofill)
    function initialCheckImage(inputEl, errorEl, setValid) {
        if (!inputEl || !errorEl) return;
        if (inputEl.files && inputEl.files.length > 0) {
            const file = inputEl.files[0];
            let ok = file.size <= MAX_SIZE;
            if (ok && file.name && file.name.length > MAX_NAME_LEN) {
                ok = false;
                errorEl.textContent = 'File name is too long (' + file.name.length + ' chars). Max allowed is ' + MAX_NAME_LEN + ' characters.';
            }
            errorEl.style.display = ok ? 'none' : 'block';
            setValid(ok);
        }
    }

    function initialCheckResume(inputEl, errorEl, setValid) {
        if (!inputEl || !errorEl) return;
        if (inputEl.files && inputEl.files.length > 0) {
            const file = inputEl.files[0];
            const ok = file.size <= MAX_SIZE;
            errorEl.style.display = ok ? 'none' : 'block';
            setValid(ok);
        }
    }

    initialCheckImage(imgInput, imgError, (v) => { imgValid = v; });
    initialCheckResume(resumeInput, resumeError, (v) => { resumeValid = v; });
    updateSubmitState();
});