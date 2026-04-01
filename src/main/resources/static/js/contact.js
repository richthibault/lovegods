(function () {
	const form      = document.getElementById('contact-form');
	const submitBtn = document.getElementById('submit-btn');
	const success   = document.getElementById('contact-success');
	const errorBox  = document.getElementById('contact-error');
	const errorMsg  = document.getElementById('contact-error-msg');
	const siteKeyEl = document.getElementById('recaptchaSiteKey');
	const tokenEl   = document.getElementById('g-recaptcha-response');

	function showError(msg) {
		errorMsg.textContent = msg;
		errorBox.hidden = false;
		errorBox.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
	}

	function clearError() {
		errorBox.hidden = true;
		errorMsg.textContent = '';
	}

	async function doSubmit(token) {
		if (tokenEl) tokenEl.value = token || '';

		const body = new URLSearchParams(new FormData(form));
		let data;
		try {
			const res = await fetch(form.action || window.location.pathname, {
				method: 'POST',
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
				body: body.toString()
			});
			data = await res.json();
		} catch (e) {
			submitBtn.disabled = false;
			submitBtn.textContent = 'Send It';
			showError('Network error — please try again.');
			return;
		}

		submitBtn.disabled = false;
		submitBtn.textContent = 'Send It';

		if (data.status === 'success') {
			form.hidden = true;
			success.hidden = false;
		} else {
			showError(data.message || 'Unknown error.');
		}
	}

	form.addEventListener('submit', async (e) => {
		e.preventDefault();
		clearError();

		if (!form.checkValidity()) {
			form.reportValidity();
			return;
		}

		submitBtn.disabled = true;
		submitBtn.textContent = 'Sending\u2026';

		if (siteKeyEl && typeof grecaptcha !== 'undefined') {
			grecaptcha.ready(() => {
				grecaptcha.execute(siteKeyEl.value, { action: 'contact_form_submit' })
					.then(token => doSubmit(token));
			});
		} else {
			doSubmit('');
		}
	});
})();