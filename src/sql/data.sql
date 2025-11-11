SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE tblBuyDetail;
TRUNCATE TABLE tblImportDetail;
TRUNCATE TABLE tblBuyBill;
TRUNCATE TABLE tblImportBill;
TRUNCATE TABLE tblCustomer;
TRUNCATE TABLE tblDeliveryStaff;
TRUNCATE TABLE tblSaleAgent;
TRUNCATE TABLE tblManager;
TRUNCATE TABLE tblStaff;
TRUNCATE TABLE tblProduct;
TRUNCATE TABLE tblProvider;
TRUNCATE TABLE tblUser;
TRUNCATE TABLE tblShop;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO tblUser(id, fullName, birth, email, phoneNumber, address, username, password, role) VALUES
(
    1,
    'Nguyễn Quang Minh',
    '2004-09-02',
    'minhnbnt@example.com',
    '0912345678',
    'Mộ Lao, Hà Đông, Hà Nội',
    'minhnbnt',
    CONCAT(
        '$argon2id$v=19$m=16384,t=2,p=1$',
        '/H4WfZHDAHLWb2LrWnMxlg$',
        'EdXWp/rSgNWePZh3fieQsm3DufXOUMkqLXE96DF5lOI'
    ),
    'customer'
),
(
    2,
    'Nguyễn Quang Minh',
    '2004-09-02',
    'm1nhnbnt@example.com',
    '0912345678',
    'Mộ Lao, Hà Đông, Hà Nội',
    'm1nhnbnt',
    CONCAT(
        '$argon2id$v=19$m=16384,t=2,p=1$',
        '/H4WfZHDAHLWb2LrWnMxlg$',
        'EdXWp/rSgNWePZh3fieQsm3DufXOUMkqLXE96DF5lOI'
    ),
    'saleAgent'
),
(
    3,
    'Lê Văn Thành',
    '1995-11-05',
    'thanhlv@example.com',
    '0945678901',
    'Đống Đa, Hà Nội',
    'thanhlv',
    CONCAT(
        '$argon2id$v=19$m=16384,t=2,p=1$',
        '/H4WfZHDAHLWb2LrWnMxlg$',
        'EdXWp/rSgNWePZh3fieQsm3DufXOUMkqLXE96DF5lOI'
    ),
    'deliveryStaff'
),
(
    4,
    'Phạm Minh Tuấn',
    '1997-06-12',
    'tuanpm@example.com',
    '0956789012',
    'Hoàng Mai, Hà Nội',
    'tuanpm',
    CONCAT(
        '$argon2id$v=19$m=16384,t=2,p=1$',
        '/H4WfZHDAHLWb2LrWnMxlg$',
        'EdXWp/rSgNWePZh3fieQsm3DufXOUMkqLXE96DF5lOI'
    ),
    'deliveryStaff'
);

INSERT INTO tblShop(columnId, name, description) VALUES
(1, 'Cửa hàng trung tâm', 'Chi nhánh chính tại Hà Nội');

INSERT INTO tblStaff(userId, shopColumn) VALUES
(2, 1),
(3, 1),
(4, 1);

INSERT INTO tblSaleAgent(staffUserId) VALUES (2);

INSERT INTO tblDeliveryStaff(staffUserId) VALUES
(3), (4);

INSERT INTO tblCustomer(id, userId) VALUES
(1, 1);

INSERT INTO tblProduct(id, name, producer, type, description, price) VALUES
(1, 'Nước ngọt Coca Cola 1,5L', 'Coca-Cola', 'Nước giải khát', 'Nước ngọt có gas vị cola', 15000),
(2, 'Nước ngọt Pepsi 1,5L', 'PepsiCo', 'Nước giải khát', 'Nước ngọt có gas vị cola', 14000),
(3, 'Nước ngọt Mirinda hương cam 1,5L', 'PepsiCo', 'Nước giải khát', 'Nước ngọt có gas vị cam', 12000),
(4, 'Nước suối Lavie 500ml', 'Lavie', 'Nước tinh khiết', 'Nước khoáng thiên nhiên', 5000),
(5, 'Trà xanh không độ 500ml', 'Coca-Cola', 'Trà', 'Trà xanh không đường', 10000),
(6, 'Sữa tươi Vinamilk 1L', 'Vinamilk', 'Sữa', 'Sữa tươi tiệt trùng', 28000),
(7, 'Bánh snack Oishi 40g', 'Oishi', 'Snack', 'Snack khoai tây vị tự nhiên', 8000),
(8, 'Mì gói Hảo Hảo 75g', 'Acecook', 'Mì ăn liền', 'Mì ăn liền vị tôm chua cay', 4000),
(9, 'Nước tăng lực Red Bull 250ml', 'Red Bull', 'Nước tăng lực', 'Nước tăng lực năng lượng', 12000),
(10, 'Kem Cornetto 90ml', 'Wall''s', 'Kem', 'Kem que socola', 15000);

INSERT INTO tblBuyBill(id, createdOn, receivedOn, customerId, saleAgentId, deliveryStaffId) VALUES
(1, '2024-01-15', NULL, 1, 2, NULL),
(2, '2024-01-16', NULL, 1, 2, NULL),
(3, '2024-01-17', NULL, 1, 2, NULL),
(4, '2024-01-18', NULL, 1, 2, NULL),

(5, '2024-01-10', '2024-01-12', 1, 2, 3),
(6, '2024-01-11', '2024-01-13', 1, 2, 4),

(7, '2024-01-14', NULL, 1, 2, 3);

INSERT INTO tblBuyDetail(productId, buyBillId, quantity) VALUES
(1, 1, 2),
(3, 1, 3),
(7, 1, 5);

INSERT INTO tblBuyDetail(productId, buyBillId, quantity) VALUES
(8, 2, 10),
(4, 2, 6),
(9, 2, 4);

INSERT INTO tblBuyDetail(productId, buyBillId, quantity) VALUES
(6, 3, 3),
(10, 3, 5);

(1, 4, 5),
(2, 4, 5),
(3, 4, 5),
(5, 4, 10),
(7, 4, 10),
(8, 4, 20),

(1, 5, 1),
(4, 5, 2);

(6, 6, 2),
(10, 6, 3);

(2, 7, 4),
(3, 7, 4),
(7, 7, 8);