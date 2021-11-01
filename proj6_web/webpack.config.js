const path = require('path');
module.exports ={
    entry:{
        main : './src/main/javascript/app.js' // teremos de o criar ou apontar para um já existente
        // podemos adicionar mais  basta criar :  [name]: <caminho_para_ficheiro>
    } ,
    output:{
        // onde exportar (substitui o código directamente!)
        path: path.join(__dirname,'/src/main/webapp/'), 
        filename: '[name]_pack.js' // sufixo pode ser alterado
    },
    module:{
        rules:[
            { // regras para lidar com ficheiros javascript
                test: /\.js$/,
                exclude: /node_modules/,
                use:{
                    loader: 'babel-loader'
                }
            },
            {// regras para lidar com os CSS importados no javascript
                test: /\.css$/,
                use: [
                  'style-loader',
                  'css-loader'
                ]
              }
        ]
    }
}
