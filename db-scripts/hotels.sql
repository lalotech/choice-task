CREATE TABLE `hotels`
(
    `id`           int(10) unsigned NOT NULL AUTO_INCREMENT,
    `name`         varchar(100) NOT NULL,
    `address`      varchar(300) NOT NULL,
    `ratting`      tinyint      NOT NULL DEFAULT 0,
    `active`       tinyint(1)   NOT NULL DEFAULT 1,
    `created_at`   datetime     NOT NULL DEFAULT current_timestamp(),
    `last_updated` timestamp    NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx1_hotel` (`id`) USING BTREE,
    UNIQUE KEY `idx2_hotel` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `hotels` (name, address, ratting)
VALUES ("Grand Resort", "Perif. Blvd. Manuel Ávila Camacho 2300, San Lucas Tepetlacalco, 54055 Tlalnepantla, Méx", 4);
INSERT INTO `hotels` (name, address, ratting)
VALUES ("Krystal Resort", "C. Gral.Francisco Villa 100, Rancho Cortes, 62120 Cuernavaca, Mor.•777 101 0353", 3);
INSERT INTO `hotels` (name, address, ratting)
VALUES ("BYD Resort", "Tezontepec 200, Lomas de Jiutepec, 62560 Jiutepec, Mor.•777 516 1010", 4);
INSERT INTO `hotels` (name, address, ratting)
VALUES ("Ibiza Plaza",
        "Avenida Costera de las Palmas S/N, Playa Revolcadero Fracc, Granjas del Marqués, 39890 Acapulco de Juárez, Gro.•800 090 9900",
        4);
INSERT INTO `hotels` (name, address, ratting)
VALUES ("Zoo Plaza",
        "Av Costera Miguel Alemán 123, Fracc Magallanes, Magallanes, 39670 Acapulco de Juárez, Gro.•744 178 1153", 5);
INSERT INTO `hotels` (name, address, ratting)
VALUES ("Real Inn", "Ignacio Manuel Altamirano, 39936 San Andrés Playa Encantada, Gro.•744 105 6347", 4);
INSERT INTO `hotels` (name, address, ratting)
VALUES ("Business Inn", "Blvd. Kukulcan Km 9.5, Zona Hotelera, 77500 Cancún, Q.R.•998 688 6448", 3);
INSERT INTO `hotels` (name, address, ratting)
VALUES ("Coral Club", "Km 14.5, Blvd. Kukulcan Lote 40-A, Zona Hotelera, 77500 Cancún, Q.R.•998 848 9600", 2);
INSERT INTO `hotels` (name, address, ratting)
VALUES ("Bugambilias Club", "Km. 18.7, Blvd. Kukulcan Lot 61, Zona Hotelera, 77500 Cancún, Q.R.•998 885 1811", 1);
INSERT INTO `hotels` (name, address, ratting, active)
VALUES ("Sky Plaza", "Blvd. Dominguez, Zona Hotelera, 77500 Cancún, Q.R.•998 885 1811", 3, 0);

CREATE TABLE `hotel_amenities`
(
    `hotel_id`   int(10) unsigned NOT NULL,
    `amenity_id` int(10) unsigned NOT NULL,
    UNIQUE KEY `hotel_amenities_idx1` (`hotel_id`,`amenity_id`) USING BTREE,
    constraint `hotel_amenities_fk1` foreign key (hotel_id) references hotels (id),
    constraint `hotel_amenities_fk2` foreign key (amenity_id) references amenities (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `hotel_amenities` (hotel_id, amenity_id)
VALUES (1, 1);
INSERT INTO `hotel_amenities` (hotel_id, amenity_id)
VALUES (1, 2);
INSERT INTO `hotel_amenities` (hotel_id, amenity_id)
VALUES (1, 3);
INSERT INTO `hotel_amenities` (hotel_id, amenity_id)
VALUES (1, 4);

INSERT INTO `hotel_amenities` (hotel_id, amenity_id)
VALUES (2, 1);
INSERT INTO `hotel_amenities` (hotel_id, amenity_id)
VALUES (2, 2);