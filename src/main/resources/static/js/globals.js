const toggle = document.querySelector('.nav-toggle');
const navLinks = document.querySelector('.nav-links');
toggle.addEventListener('click', () => {
	const expanded = toggle.getAttribute('aria-expanded') === 'true';
	toggle.setAttribute('aria-expanded', !expanded);
	navLinks.classList.toggle('open');
});

document.addEventListener('DOMContentLoaded', () => {
	//console.log('Setting active nav link based on current path');
	const currentPath = window.location.pathname;
	document.querySelectorAll('.nav-links a').forEach(link => {
		const linkPath = new URL(link.href).pathname;
		//console.log(`Checking link: ${linkPath} against current path: ${currentPath}`);
		// Exact match for root; prefix match for everything else
		const isActive = linkPath === '/'
			? currentPath === '/'
			: currentPath === linkPath || currentPath.startsWith(linkPath + '/');
		link.classList.toggle('active', isActive);
	});
});

document.addEventListener('DOMContentLoaded', () => {
	const startYear = 2026;
	const currentYear = new Date().getFullYear();
	const str = currentYear > startYear ? `${startYear}-${currentYear}` : currentYear;
	document.querySelectorAll('.copyright-years').forEach(el => el.textContent = str);
});
