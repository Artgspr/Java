/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  ra2357094
 * Created: 6 de mar. de 2025
 */
Create table Usuario (
 id SERIAL PRIMARY KEY,
 nome VARCHAR (255),
 email VARCHAR (255),
 senha VARCHAR (255),
 login VARCHAR (255)
)