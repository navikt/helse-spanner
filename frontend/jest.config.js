module.exports = {
  preset: "ts-jest",
  testEnvironment: "jsdom",
  moduleFileExtensions: ["js", "jsx", "json", "tsx", "ts"],
  transform: {
    ".+\\.css$": "esbuild-jest",
    "^.+\\.tsx?$": "esbuild-jest"
  },
  transformIgnorePatterns: ["<rootDir>/node_modules"],
  watchPathIgnorePatterns: ["<rootDir>/dist", "<rootDir>/node_modules"]
};
