<?php
    include "infectious_request.php";

    $req = new InfectiousRequest($_SERVER['REMOTE_ADDR']);
    $req->begin();
    $res = $req->is_available();
    $xml = new DOMDocument("1.0");
    $xml->formatOutput = true;
    $root = $xml->createElement('infectious');
    $root = $xml->appendChild($root);

    // An opponent is available
    if($res) {
        $req->update_entry();
        $elem = $xml->createElement('result', $req->get_opponent_ip());
        $elem = $root->appendChild($elem);
        $elem->setAttribute('value', true);
        $elem->setAttribute('master', true);
        $req->commit();
    } else {
        $req->add_entry();
        $req->commit();
        $total_secs = 0;
        while($total_secs <= 30) {
            sleep(5);
            $req->begin();
            $res = $req->is_responded();

            // Request was responded
            if($res) {
                $req->delete_entry();
                $response = $req->get_opponent_ip();
                $elem = $xml->createElement('result', $req->get_opponent_ip());
                $elem = $root->appendChild($elem);
				$elem->setAttribute('value', true);
                $elem->setAttribute('master', false);
                $req->commit();
				$found = true;
                break;
            } else {
                $total_secs += 5;
                $req->commit();
            }
        }
        if(!$found) {
            $elem = $xml->createElement('result', $req->get_opponent_ip());
            $elem = $root->appendChild($elem);
            $elem->setAttribute('value', false);
        }
        $req->begin();
        $req->delete_entry();
        $req->commit();
    }
    echo $xml->saveXML();
?>
