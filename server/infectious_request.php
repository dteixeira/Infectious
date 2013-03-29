<?php
    class InfectiousRequest {

        // class variables
        private static $db_name = "sqlite:match.sqlite3";

        // variables
        private $client_ip;
        private $db;
        private $opponent_ip;

        public function __construct($ip) {
            $this->client_ip = $ip;
            try {
                $this->db = new PDO(InfectiousRequest::$db_name);
            } catch (PDOException $e) {
                echo $e->getMessage();
            }
        }

        public function begin() {
            $this->db->exec("BEGIN EXCLUSIVE TRANSACTION;");
        }

        public function commit() {
            $this->db->exec("COMMIT TRANSACTION");
        }

        public function close_db() {
            $this->db = null;
        }

        public function is_responded() {
            $stmt = $this->db->prepare("SELECT * FROM Match WHERE client1_ip = :ip AND client2_ip NOT NULL;");
            $stmt->execute(array('ip' => $this->client_ip));
            $row = $stmt->fetch(PDO::FETCH_ASSOC);
            if(!$row)
                return false;
            else {
                $this->opponent_ip = $row['client2_ip'];
                return true;
            }
        }

        public function is_available() {
            $stmt = $this->db->prepare("SELECT * FROM Match WHERE client2_ip IS NULL;");
            $stmt->execute();
            $row = $stmt->fetch(PDO::FETCH_ASSOC);
            if(!$row)
                return false;
            else {
                $this->opponent_ip = $row['client1_ip'];
                return true;
            }
        }

        public function update_entry() {
            $stmt = $this->db->prepare("UPDATE Match set client2_ip = :my_ip WHERE client1_ip = :opponent_ip;");
            $stmt->execute(array('my_ip' => $this->client_ip, 'opponent_ip' => $this->opponent_ip));
        }

        public function add_entry() {
            $stmt = $this->db->prepare("INSERT INTO Match (client1_ip, client2_ip) VALUES (:ip, NULL);");
            $stmt->execute(array('ip' => $this->client_ip));
        }

        public function delete_entry() {
            $stmt = $this->db->prepare("DELETE FROM Match WHERE client1_ip = :ip;");
            $stmt->execute(array('ip' => $this->client_ip));
        }

        public function get_opponent_ip() {
            return $this->opponent_ip;
        }

    }
?>
