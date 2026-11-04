-- Pegar en phpMyAdmin -> base de datos ut2 -> pestaña SQL
-- DELIMITER: el cuerpo tiene ";" dentro, así que se cambia el delimitador a //
-- temporalmente para que MySQL no piense que el CREATE PROCEDURE acaba en el primer ";".

DELIMITER //

CREATE PROCEDURE CalcularPrecipitacionTotal(
    IN  estacionNombre     VARCHAR(15),      -- entrada: nombre de la estación
    OUT totalPrecipitacion DECIMAL(10, 2)    -- salida: suma de la lluvia
)
BEGIN
    SELECT SUM(Precipitacion) INTO totalPrecipitacion   -- INTO guarda el resultado en el OUT
    FROM datos_climaticos
    WHERE Estacion = estacionNombre
      AND MONTH(Fecha) = 10;                            -- solo octubre
END //

DELIMITER ;

-- Probarlo en phpMyAdmin:
-- CALL CalcularPrecipitacionTotal('Algemesi', @total);
-- SELECT @total;      -- debe dar 254.00
