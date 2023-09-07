module.exports = {
  purge: ["./src/**/*.{js,jsx,ts,tsx}", "./public/index.html"],
  darkMode: false,
  theme: {
    extend: {
      colors: {
        "custom-blue": "#1E1E1E",
        "custom-white": "#fff",
        "custom-black": "#000000",
        "custom-black2": "#121212",
        "custom-gray": "#A0A0A0",
        "custom-darkgray": "rgba(217, 217, 217, 0.10)",
        "custom-lightpurple": "#C0CBE7",
        "custom-lightblue" : "#BDCAE8",
        "custom-lightgray" : "#D9D9D9",
        "custom-background" : "bg-gradient-to-t from-gray-900 via-stone-800 to-gray-400"
        
      },
    },
  },
  variants: {
    extend: {
      placeholderColor: ["responsive", "dark", "focus", "hover", "active"],
    },
  },
  plugins: [],
};
