import { defineConfig } from '@kubb/core'
import { pluginClient } from '@kubb/swagger-client'
import { pluginOas } from '@kubb/plugin-oas'

export default defineConfig({
  root: '.',
  input: {
    path: '../src/main/resources/contract/elior.openapi.yaml'
  },

  output: {
    path: './src/gen',
    clean: true
  },
  plugins: [
    pluginOas({
      output: false
    }),
    pluginClient({
      output: {
        path: './clients/axios'
      },
      client: {
        importPath: '@/services/axios'
      },
      dataReturnType: 'full',
      group: { type: 'tag', output: './clients/axios/{{tag}}Service' }
    })
  ]
})
