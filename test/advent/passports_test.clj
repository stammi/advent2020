(ns advent.passports-test
  (:require [clojure.test :refer :all]
            [advent.passports :refer :all]))

(def test-db "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\nbyr:1937 iyr:2017 cid:147 hgt:183cm\n\niyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884\nhcl:#cfa07d byr:1929\n\nhcl:#ae17e1 iyr:2013\neyr:2024\necl:brn pid:760753108 byr:1931\nhgt:179cm\n\nhcl:#cfa07d eyr:2025 pid:166559648\niyr:2011 ecl:brn hgt:59in")

(deftest parsig-passports
  (testing "parsing a single passport"
    (is (= {"byr" "1937"
            "cid" "147"
            "ecl" "gry"
            "eyr" "2020"
            "hcl" "#fffffd"
            "hgt" "183cm"
            "iyr" "2017"
            "pid" "860033327"}
           (read-passport "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\nbyr:1937 iyr:2017 cid:147 hgt:183cm"))))

  (testing "parsing the whole db"
    (let [passports (read-passport-db test-db)]
      (is (= 4 (count passports)))
      (is (= {"byr" "1929"
              "cid" "350"
              "ecl" "amb"
              "eyr" "2023"
              "hcl" "#cfa07d"
              "iyr" "2013"
              "pid" "028048884"}
             (second passports))))))

;------ part 1: simple validation

(deftest validating-passports
  (testing "the test passports are validated as expected"
    (let [passports (read-passport-db test-db)]
      (is (= [true false true false] (map validate passports))))))

;------ part 2: deep validation
(deftest field-validators
  (testing "year validator"
    (is (year-between 1920 2002 "1977"))
    (is (year-between 1920 2002 "1920"))
    (is (year-between 1920 2002 "2002"))
    (is (not (year-between 1920 2002 "1909")))
    (is (not (year-between 1920 2002 "2003"))))

  (testing "height validator"
    (is (height-between {:min 159 :max 193 :unit "cm"} "159cm"))
    (is (not (height-between {:min 159 :max 193 :unit "cm"} "158cm")))
    (is (height-valid "166cm"))
    (is (height-valid "60in"))
    (is (not (height-valid "30in")))
    (is (not (height-valid "10cm"))))

  (testing "hair-color validator"
    (is (hair-color-valid "#123456"))
    (is (hair-color-valid "#1234a6"))
    (is (not (hair-color-valid "#12A4a6"))))

  (testing "eye-color validator"
    (is (eye-color-valid "amb"))
    (is (not (eye-color-valid "foo"))))

  (testing "passport-id validator"
    (is (pid-valid "087499704"))
    (is (not (pid-valid "08744")))
    (is (not (pid-valid "087441111111111111")))))

(def deep-invalid-db "eyr:1972 cid:100\nhcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926\n\niyr:2019\nhcl:#602927 eyr:1967 hgt:170cm\necl:grn pid:012533040 byr:1946\n\nhcl:dab227 iyr:2012\necl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277\n\nhgt:59cm ecl:zzz\neyr:2038 hcl:74454a iyr:2023\npid:3556412378 byr:2007")

(def deep-valid-db "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980\nhcl:#623a2f\n\neyr:2029 ecl:blu cid:129 byr:1989\niyr:2014 pid:896056539 hcl:#a97842 hgt:165cm\n\nhcl:#888785\nhgt:164cm byr:2001 iyr:2015 cid:88\npid:545766238 ecl:hzl\neyr:2022")


(deftest deep-validating-passports
  (testing "the test passports are validated as expected"
    (let [passports (read-passport-db deep-valid-db)]
      (is (every? deep-validate passports))))
  (testing "the test passports are invalidated as expected"
    (let [passports (read-passport-db deep-invalid-db)]
      (is (not-any? deep-validate passports)))))





