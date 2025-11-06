CREATE TABLE tblUser (
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fullName VARCHAR(255) NOT NULL,
    birth DATE,
    email VARCHAR(255) NOT NULL UNIQUE,
    phoneNumber VARCHAR(10),
    address VARCHAR(255),
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE tblShop (
    columnId INT(10) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE tblStaff (
    userId INT(10) UNSIGNED NOT NULL PRIMARY KEY,
    shopColumn INT(10) UNSIGNED NOT NULL,
    FOREIGN KEY (userId) REFERENCES tblUser(id),
    FOREIGN KEY (shopColumn) REFERENCES tblShop(columnId)
);

CREATE TABLE tblSaleAgent (
    staffUserId INT(10) UNSIGNED NOT NULL PRIMARY KEY,
    FOREIGN KEY (staffUserId) REFERENCES tblStaff(userId)
);

CREATE TABLE tblDeliveryStaff (
    staffUserId INT(10) UNSIGNED NOT NULL PRIMARY KEY,
    FOREIGN KEY (staffUserId) REFERENCES tblStaff(userId)
);

CREATE TABLE tblManager (
    staffUserId INT(10) UNSIGNED NOT NULL PRIMARY KEY,
    FOREIGN KEY (staffUserId) REFERENCES tblStaff(userId)
);

CREATE TABLE tblCustomer (
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userId INT(10) UNSIGNED NOT NULL,
    FOREIGN KEY (userId) REFERENCES tblUser(id)
);

CREATE TABLE tblProduct (
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    producer VARCHAR(255),
    type VARCHAR(255),
    description VARCHAR(255),
    price FLOAT(11) NOT NULL
);

CREATE TABLE tblProvider (
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE tblBuyBill (
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    createdOn DATE NOT NULL,
    receivedOn DATE,
    customerId INT(10) UNSIGNED NOT NULL,
    saleAgentId INT(10) UNSIGNED NOT NULL,
    deliveryStaffId INT(10) UNSIGNED,
    FOREIGN KEY (customerId) REFERENCES tblCustomer(id),
    FOREIGN KEY (saleAgentId) REFERENCES tblSaleAgent(staffUserId),
    FOREIGN KEY (deliveryStaffId) REFERENCES tblDeliveryStaff(staffUserId)
);

CREATE TABLE tblBuyDetail (
    productId INT(10) UNSIGNED NOT NULL,
    buyBillId INT(10) UNSIGNED NOT NULL,
    quantity INT(11) NOT NULL,
    PRIMARY KEY (productId, buyBillId),
    FOREIGN KEY (productId) REFERENCES tblProduct(id),
    FOREIGN KEY (buyBillId) REFERENCES tblBuyBill(id)
);

CREATE TABLE tblImportBill (
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    managerStaffUserId INT(10) UNSIGNED NOT NULL,
    providerId INT(10) UNSIGNED NOT NULL,
    FOREIGN KEY (managerStaffUserId) REFERENCES tblManager(staffUserId),
    FOREIGN KEY (providerId) REFERENCES tblProvider(id)
);

CREATE TABLE tblImportDetail (
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    productId INT(10) UNSIGNED NOT NULL,
    importBillId INT(10) UNSIGNED NOT NULL,
    FOREIGN KEY (productId) REFERENCES tblProduct(id),
    FOREIGN KEY (importBillId) REFERENCES tblImportBill(id)
);