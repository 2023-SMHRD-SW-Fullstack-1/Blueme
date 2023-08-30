module.exports = {
  purge: ["./src/**/*.{js,jsx,ts,tsx}", "./public/index.html"],
  darkMode: false,
  theme: {
    extend: {},
    colors: {
      "custom-blue": "#080B16",
      "custom-white": "#fff",
      "custom-black": "#000000",
      "custom-black2": "#121212",
    },
  },
  variants: {
    extend: {
      placeholderColor: ["responsive", "dark", "focus", "hover", "active"],
    },
  },
  plugins: [],
};
