(function () {
	const track   = document.querySelector('.carousel-track');
	const dots    = document.querySelectorAll('.carousel-dot');
	const btnPrev = document.getElementById('btn-prev');
	const btnNext = document.getElementById('btn-next');
	const total   = document.querySelectorAll('.carousel-slide').length;
	let current = 0;

	function goTo(index) {
		current = Math.max(0, Math.min(index, total - 1));
		track.style.transform = `translateX(-${current * 100}%)`;
		dots.forEach((dot, i) => {
			dot.classList.toggle('active', i === current);
			dot.setAttribute('aria-selected', i === current);
		});
		btnPrev.disabled = current === 0;
		btnNext.disabled = current === total - 1;
	}

	btnPrev.addEventListener('click', () => goTo(current - 1));
	btnNext.addEventListener('click', () => goTo(current + 1));
	dots.forEach((dot, i) => dot.addEventListener('click', () => goTo(i)));

	goTo(0);
})();