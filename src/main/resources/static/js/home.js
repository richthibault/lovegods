document.querySelectorAll('.photo-frame').forEach(frame => {
	const img = frame.querySelector('img');
	const measure = () => {
		if (!img.naturalWidth) return;
		const maxW = Math.min(img.naturalWidth, 440);
		let w = maxW, h = Math.round(maxW * img.naturalHeight / img.naturalWidth);
		if (h > 520) { h = 520; w = Math.round(520 * img.naturalWidth / img.naturalHeight); }
		frame.style.setProperty('--expand-w', w + 'px');
		frame.style.setProperty('--expand-h', h + 'px');
	};
	img.complete ? measure() : img.addEventListener('load', measure);
});