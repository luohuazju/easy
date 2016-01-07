I already have PHP
>php --version
PHP 5.6.16 (cli) (built: Dec 26 2015 18:37:18) 
Copyright (c) 1997-2015 The PHP Group
Zend Engine v2.6.0, Copyright (c) 1998-2015 Zend Technologies
    with Zend OPcache v7.0.6-dev, Copyright (c) 1999-2015, by Zend Technologies
    with Xdebug v2.2.5, Copyright (c) 2002-2014, by Derick Rethans

>curl -sS https://getcomposer.org/installer | php

>php composer.phar install

Run the test class
>phpunit --bootstrap vendor/autoload.php tests/EventTest