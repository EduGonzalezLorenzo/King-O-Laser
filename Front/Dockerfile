# Etapa de compilación
FROM node:19.6.0-alpine as builder
# Se define el directorio de trabajo
WORKDIR /app
# Se copian todos los fichero del directorio actual al directorio de trabajo
COPY . .
# Se instalan los modulos de node y se instala la aplicación
RUN yarn install && yarn build && yarn generate

# Etapa de ejecución
FROM nginx:alpine
# Se define el directorio de trabajo
WORKDIR /usr/share/nginx/html
# Se borran los assets estaticos predeterminados de Nginx
RUN rm -rf ./*
# Se copian los assets estaticos creados en la etapa de compialción
COPY --from=builder /app/dist .
# Se ejecuta el contenedor nginx con loas directivas globales y daemon apagado
ENTRYPOINT ["nginx", "-g", "daemon off;"]