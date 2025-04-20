--- SQLite3 scripts to create database for AIMS project ---
--
--
CREATE TABLE "Product" (
  "productID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "title" TEXT NOT NULL,
  "category" TEXT NOT NULL,
  "value" REAL NOT NULL,
  "price" REAL NOT NULL,
  "quantity" INT NOT NULL DEFAULT 0,
  "description" TEXT,
  "imageURL" TEXT NOT NULL,
  "barCode" TEXT,
  "warehouseEntryDate" DATE DEFAULT CURRENT_DATE,
  "dimensions" TEXT,
  "weight" REAL,
  "warehouseProvince" TEXT,
  "warehouseDistrict" TEXT,
  "warehouseAddress" TEXT,
  "createdAt" DATETIME DEFAULT CURRENT_TIMESTAMP,
  "updatedAt" DATETIME DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE "CD"(
  "productID" INTEGER PRIMARY KEY NOT NULL,
  "artist" TEXT NOT NULL,
  "recordLabel" TEXT NOT NULL,
  "tracklist" TEXT,
  "genre" TEXT NOT NULL,
  "releasedDate" DATE,
  CONSTRAINT "fk_CD_Product1"
    FOREIGN KEY("productID")
    REFERENCES "Product"("productID")
);
CREATE TABLE "Book"(
  "productID" INTEGER PRIMARY KEY NOT NULL,
  "author" TEXT NOT NULL,
  "coverType" TEXT NOT NULL,
  "publisher" TEXT NOT NULL,
  "publicationDate" DATE NOT NULL,
  "numOfPages" INTEGER NOT NULL,
  "language" TEXT NOT NULL,
  "genre" TEXT NOT NULL,
  CONSTRAINT "fk_Book_Product1"
    FOREIGN KEY("productID")
    REFERENCES "Product"("productID")
);
CREATE TABLE "DVD" (
  "productID" INTEGER PRIMARY KEY NOT NULL,
  "discType" TEXT NOT NULL,
  "director" TEXT NOT NULL,
  "runtime" TEXT NOT NULL,
  "studio" TEXT NOT NULL,
  "language" TEXT NOT NULL,
  "subtitle" TEXT NOT NULL,
  "releaseDate" DATE NOT NULL,
  "genre" TEXT NOT NULL,
  CONSTRAINT "fk_DVD_Product1"
    FOREIGN KEY ("productID")
    REFERENCES "Product"("productID")
);

CREATE TABLE "DeliveryInfo" (
  "deliveryInfoID" INTEGER PRIMARY KEY AUTOINCREMENT,
  "recipientName" TEXT NOT NULL,
  "email" TEXT NOT NULL,
  "phoneNumber" TEXT NOT NULL,
  "province" TEXT NOT NULL,
  "district" TEXT NOT NULL,
  "address" TEXT NOT NULL,
  "deliveryMethod" TEXT NOT NULL,
  "deliveryTime" REAL,
  "isRushDeliveryEligible" BOOLEAN NOT NULL,
  "deliveryInstructions" TEXT,
  "createdAt" DATETIME DEFAULT CURRENT_TIMESTAMP,
  "updatedAt" DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE "Card"(
  "cardID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "cardCode" TEXT NOT NULL,
  "owner" TEXT NOT NULL,
  "cvvCode" TEXT NOT NULL,
  "dateExpired" TEXT NOT NULL
);

CREATE TABLE "User" (
  "userID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "username" TEXT NOT NULL UNIQUE,
  "email" TEXT NOT NULL,
  "password" TEXT NOT NULL,
  "role" TEXT NOT NULL,
  "status" BOOLEAN NOT NULL,
  "createdAt" DATETIME DEFAULT CURRENT_TIMESTAMP,
  "updatedAt" DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE "Cart" (
  "cartID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "sessionID" TEXT,
  "createdAt" DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE "CartItem" (
  "cartID" INTEGER NOT NULL,
  "productID" INTEGER NOT NULL,
  "quantity" INTEGER NOT NULL,
  "price" REAL NOT NULL,
  PRIMARY KEY ("cartID", "productID"),
  FOREIGN KEY ("cartID") REFERENCES "Cart"("cartID"),
  FOREIGN KEY ("productID") REFERENCES "Product"("productID")
);

CREATE TABLE "Order" (
  "orderID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "status" CHARACTER(15) NOT NULL,
  "shippingFee" REAL NOT NULL,
  "deliveryInfoID" INTEGER NOT NULL,
  FOREIGN KEY ("deliveryInfoID") REFERENCES "DeliveryInfo"("deliveryInfoID")
);

CREATE TABLE "OrderItem" (
  "orderID" INTEGER NOT NULL,
  "productID" INTEGER NOT NULL,
  "quantity" INTEGER NOT NULL,
  "unitPrice" REAL NOT NULL,
  "subtotal" REAL NOT NULL,
  "isRushDeliveryEligible" BOOLEAN NOT NULL,
  "weight" REAL,
  PRIMARY KEY ("orderID", "productID"),
  FOREIGN KEY ("orderID") REFERENCES "Order"("orderID"),
  FOREIGN KEY ("productID") REFERENCES "Product"("productID")
);

CREATE TABLE "Invoice" (
  "invoiceID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "orderID" INTEGER NOT NULL,
  "issueDate" DATE NOT NULL,
  "subtotal" REAL NOT NULL,
  "VAT" REAL NOT NULL,
  "totalWithVAT" REAL NOT NULL,
  "regularDeliveryFee" REAL NOT NULL,
  "rushDeliveryFee" REAL NOT NULL,
  "totalAmount" REAL NOT NULL,
  "paymentMethod" TEXT NOT NULL,
  "VAT_RATE" REAL NOT NULL,
  FOREIGN KEY ("orderID") REFERENCES "Order"("orderID")
);

CREATE TABLE "PaymentTransaction" (
  "transactionID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "amount" REAL NOT NULL,
  "paymentMethod" TEXT NOT NULL,
  "transactionDate" DATE NOT NULL,
  "status" TEXT NOT NULL,
  "content" TEXT,
  "cardID" INTEGER NOT NULL,
  "invoiceID" INTEGER NOT NULL,
  FOREIGN KEY ("cardID") REFERENCES "Card"("cardID"),
  FOREIGN KEY ("invoiceID") REFERENCES "Invoice"("invoiceID")
);



CREATE INDEX "idx_product_category" ON "Product"("category");
CREATE INDEX "idx_product_title" ON "Product"("title");
CREATE INDEX "idx_order_status" ON "Order"("status");

CREATE TRIGGER "product_updatedAt_tg"
AFTER UPDATE ON "Product"
BEGIN
  UPDATE "Product"
  SET "updatedAt" = DATETIME('NOW', 'localtime')
  WHERE "productID" = OLD."productID";
END;

CREATE TRIGGER "user_updatedAt_tg"
AFTER UPDATE ON "User"
FOR EACH ROW
BEGIN
  UPDATE "User"
  SET "updatedAt" = DATETIME('NOW', 'localtime')
  WHERE "userID" = OLD."userID";
END;


