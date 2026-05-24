(function () {
	const panels   = [...document.querySelectorAll('.gallery-panel')];
	const tabs     = [...document.querySelectorAll('.gallery-tab')];
	const lightbox = document.getElementById('lightbox');
	const img      = document.getElementById('lightbox-img');
	const counter  = document.getElementById('lightbox-counter');
	const btnPrev  = document.getElementById('lightbox-prev');
	const btnNext  = document.getElementById('lightbox-next');

	let images = [];
	let thumbs = [];
	let current = 0;

	function bindPanel(panel) {
		thumbs = [...panel.querySelectorAll('.gallery-thumb')];
		images = thumbs.map(el => ({ src: el.dataset.src, alt: el.dataset.alt }));
		thumbs.forEach((el, i) => {
			el.onclick = () => open(i);
		});
	}

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
		if (thumbs[current]) thumbs[current].focus();
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

	function activateTab(tab) {
		const targetId = tab.dataset.panel;
		tabs.forEach(t => {
			const isActive = t === tab;
			t.classList.toggle('is-active', isActive);
			t.setAttribute('aria-selected', isActive ? 'true' : 'false');
			t.tabIndex = isActive ? 0 : -1;
		});
		panels.forEach(p => {
			const isActive = p.id === `panel-${targetId}`;
			p.classList.toggle('is-active', isActive);
			if (isActive) {
				p.removeAttribute('hidden');
				bindPanel(p);
			} else {
				p.setAttribute('hidden', '');
			}
		});
	}

	tabs.forEach((tab, i) => {
		tab.addEventListener('click', () => activateTab(tab));
		tab.addEventListener('keydown', e => {
			if (e.key !== 'ArrowLeft' && e.key !== 'ArrowRight') return;
			e.preventDefault();
			const delta = e.key === 'ArrowRight' ? 1 : -1;
			const next = (i + delta + tabs.length) % tabs.length;
			tabs[next].focus();
			activateTab(tabs[next]);
		});
	});

	// Initialize with the active panel
	const activePanel = panels.find(p => p.classList.contains('is-active')) || panels[0];
	if (activePanel) bindPanel(activePanel);

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
