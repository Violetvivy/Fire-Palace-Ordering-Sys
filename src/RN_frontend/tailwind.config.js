// tailwind.config.js
module.exports = {
  // 重点：包含所有组件和页面的路径
  content: ['./src/**/*.{js,jsx,ts,tsx}', './components/**/*.{js,jsx,ts,tsx}'],
  presets: [require('nativewind/preset')],
  theme: {
    extend: {},
  },
  plugins: [],
};
