(function () {
	const thumbs   = [...document.querySelectorAll('.gallery-thumb')];
	const images   = thumbs.map(el => ({ src: el.dataset.src, alt: el.dataset.alt }));
	const lightbox = document.getElementById('lightbox');
	const img      = document.getElementById('lightbox-img');
	const counter  = document.getElementById('lightbox-counter');
	const btnPrev  = document.getElementById('lightbox-prev');
	const btnNext  = document.getElementById('lightbox-next');
	let current = 0;

	function updateButtons() {
		btnPrev.disabled = current === 0;
		btnNext.disabled = current === images.length - 1;
		counter.textContent = `${current + 1} / ${images.length}`;
	}

	function open(index) {
		current = index;
		img.src = images[current].src;
		img.alt = images[current].alt;
		lightbox.classList.add('open');
		lightbox.setAttribute('aria-hidden', 'false');
		document.body.style.overflow = 'hidden';
		updateButtons();
		btnPrev.focus();
	}

	function close() {
		lightbox.classList.remove('open');
		lightbox.setAttribute('aria-hidden', 'true');
		document.body.style.overflow = '';
		thumbs[current].focus();
	}

	function navigate(delta) {
		const next = current + delta;
		if (next < 0 || next >= images.length) return;
		img.classList.add('fading');
		setTimeout(() => {
			current = next;
			img.src = images[current].src;
			img.alt = images[current].alt;
			img.classList.remove('fading');
			updateButtons();
		}, 140);
	}

	thumbs.forEach((el, i) => el.addEventListener('click', () => open(i)));
	document.getElementById('lightbox-close').addEventListener('click', close);
	btnPrev.addEventListener('click', () => navigate(-1));
	btnNext.addEventListener('click', () => navigate(1));

	// Click backdrop to close
	lightbox.addEventListener('click', e => { if (e.target === lightbox) close(); });

	// Keyboard navigation
	document.addEventListener('keydown', e => {
		if (!lightbox.classList.contains('open')) return;
		if (e.key === 'Escape')     close();
		if (e.key === 'ArrowLeft')  navigate(-1);
		if (e.key === 'ArrowRight') navigate(1);
	});
})();