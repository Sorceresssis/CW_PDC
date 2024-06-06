import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'


// https://vitejs.dev/config/
export default defineConfig({
	base: './',
	root: resolve(__dirname, './src'),
	build: {
		minify: true,
		outDir: resolve(__dirname, './dist'),
		rollupOptions: {
			input: {
				index: resolve(__dirname, './src/index.html'),
				admin: resolve(__dirname, './src/backstage/index.html')
			}
		}
	},
	plugins: [vue()],
	server: {
		host: '0.0.0.0',
	}
})

