
CREATE TABLE "Product" (
  "productID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "title" VARCHAR(45) NOT NULL,
  "category" VARCHAR(45) NOT NULL,
  "value" FLOAT NOT NULL,
  "price" FLOAT NOT NULL,
  "quantity" INT NOT NULL,
  "description" VARCHAR(1000),
  "imageURL" VARCHAR(45) NOT NULL,
  "barCode" VARCHAR(100),
  "warehouseEntryDate" DATE,
  "dimensions" VARCHAR(100),
  "weight" FLOAT,
  "warehouseProvince" VARCHAR(100),
  "warehouseDistrict" VARCHAR(100),
  "warehouseAddress" VARCHAR(255)
);
CREATE TABLE "CD"(
  "productID" INTEGER PRIMARY KEY NOT NULL,
  "artist" VARCHAR(100) NOT NULL,
  "recordLabel" VARCHAR(100) NOT NULL,
  "tracklist" VARCHAR(1000),
  "genre" VARCHAR(100) NOT NULL,
  "releasedDate" DATE,
  CONSTRAINT "fk_CD_Product1"
    FOREIGN KEY("productID")
    REFERENCES "Product"("productID")
);
CREATE TABLE "Book"(
  "productID" INTEGER PRIMARY KEY NOT NULL,
  "author" VARCHAR(100) NOT NULL,
  "coverType" VARCHAR(45) NOT NULL,
  "publisher" VARCHAR(100) NOT NULL,
  "publicationDate" DATE NOT NULL,
  "numOfPages" INTEGER NOT NULL,
  "language" VARCHAR(45) NOT NULL,
  "genre" VARCHAR(100) NOT NULL,
  CONSTRAINT "fk_Book_Product1"
    FOREIGN KEY("productID")
    REFERENCES "Product"("productID")
);
CREATE TABLE "DVD" (
  "productID" INTEGER PRIMARY KEY NOT NULL,
  "discType" VARCHAR(45) NOT NULL,
  "director" VARCHAR(100) NOT NULL,
  "runtime" VARCHAR(20) NOT NULL,
  "studio" VARCHAR(100) NOT NULL,
  "language" VARCHAR(45) NOT NULL,
  "subtitle" VARCHAR(45) NOT NULL,
  "releaseDate" DATE NOT NULL,
  "genre" VARCHAR(100) NOT NULL,
  CONSTRAINT "fk_DVD_Product1"
    FOREIGN KEY ("productID")
    REFERENCES "Product"("productID")
);

CREATE TABLE "DeliveryInfo" (
  "deliveryInfoID" INTEGER PRIMARY KEY AUTOINCREMENT,
  "recipientName" VARCHAR(100) NOT NULL,
  "email" VARCHAR(100) NOT NULL,
  "phoneNumber" VARCHAR(20) NOT NULL,
  "province" VARCHAR(45) NOT NULL,
  "district" VARCHAR(45) NOT NULL,
  "address" VARCHAR(200) NOT NULL,
  "deliveryMethod" VARCHAR(20) NOT NULL,
  "deliveryTime" FLOAT,
  "isRushDeliveryEligible" BOOLEAN NOT NULL,
  "deliveryInstructions" VARCHAR(255)
);

CREATE TABLE "Card"(
  "cardID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "cardCode" VARCHAR(15) NOT NULL,
  "owner" VARCHAR(45) NOT NULL,
  "cvvCode" VARCHAR(3) NOT NULL,
  "dateExpired" VARCHAR(4) NOT NULL
);

CREATE TABLE "User" (
  "userID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "username" VARCHAR(50) NOT NULL UNIQUE,
  "email" VARCHAR(100) NOT NULL,
  "password" VARCHAR(255) NOT NULL,
  "role" VARCHAR(45) NOT NULL,
  "status" BOOLEAN NOT NULL
);

CREATE TABLE "Cart" (
  "cartID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "sessionID" VARCHAR NOT NULL,
  "createdDate" DATE NOT NULL,
  "updatedDate" DATE NOT NULL
);

CREATE TABLE "CartItem" (
  "cartID" INTEGER NOT NULL,
  "productID" INTEGER NOT NULL,
  "quantity" INTEGER NOT NULL,
  "price" FLOAT NOT NULL,
  PRIMARY KEY ("cartID", "productID"),
  FOREIGN KEY ("cartID") REFERENCES "Cart"("cartID"),
  FOREIGN KEY ("productID") REFERENCES "Product"("productID")
);

CREATE TABLE "Order" (
  "orderID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "status" VARCHAR NOT NULL,
  "shippingFee" FLOAT NOT NULL,
  "deliveryInfoID" INTEGER NOT NULL,
  FOREIGN KEY ("deliveryInfoID") REFERENCES "DeliveryInfo"("deliveryInfoID")
);

CREATE TABLE "OrderItem" (
  "orderID" INTEGER NOT NULL,
  "productID" INTEGER NOT NULL,
  "quantity" INTEGER NOT NULL,
  "unitPrice" FLOAT NOT NULL,
  "subtotal" FLOAT NOT NULL,
  "isRushDeliveryEligible" BOOLEAN NOT NULL,
  "weight" FLOAT,
  PRIMARY KEY ("orderID", "productID"),
  FOREIGN KEY ("orderID") REFERENCES "Order"("orderID"),
  FOREIGN KEY ("productID") REFERENCES "Product"("productID")
);

CREATE TABLE "Invoice" (
  "invoiceID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "orderID" INTEGER NOT NULL,
  "issueDate" DATE NOT NULL,
  "subtotal" FLOAT NOT NULL,
  "VAT" FLOAT NOT NULL,
  "totalWithVAT" FLOAT NOT NULL,
  "regularDeliveryFee" FLOAT NOT NULL,
  "rushDeliveryFee" FLOAT NOT NULL,
  "totalAmount" FLOAT NOT NULL,
  "paymentMethod" VARCHAR(45) NOT NULL,
  "VAT_RATE" FLOAT NOT NULL,
  FOREIGN KEY ("orderID") REFERENCES "Order"("orderID")
);

CREATE TABLE "PaymentTransaction" (
  "transactionID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "amount" FLOAT NOT NULL,
  "paymentMethod" VARCHAR(45) NOT NULL,
  "transactionDate" DATE NOT NULL,
  "status" VARCHAR(20) NOT NULL,
  "content" VARCHAR(255),
  "cardID" INTEGER NOT NULL,
  "invoiceID" INTEGER NOT NULL,
  FOREIGN KEY ("cardID") REFERENCES "Card"("cardID"),
  FOREIGN KEY ("invoiceID") REFERENCES "Invoice"("invoiceID")
);

