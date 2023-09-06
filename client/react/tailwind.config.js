module.exports = {
    purge: ['./src/**/*.{js,jsx,ts,tsx}', './public/index.html'],
    darkMode: false,
    theme: {
        extend: {},
        colors: {
            'custom-blue': '#080B16',
            'custom-white': '#fff',
            'custom-black': '#000000',
            'custom-black2': '#121212',
            'custom-gray': '#A0A0A0',
            'custom-darkgray': 'rgba(217, 217, 217, 0.10)',
            'custom-lightpurple': '#C0CBE7',
            // 유영 글씨 색 추가
            'custom-recappdes' : '#BDCAE8',
            //유영 끝
        },
    },
    variants: {
        extend: {
            placeholderColor: ['responsive', 'dark', 'focus', 'hover', 'active'],
        },
    },
    plugins: [],
};
