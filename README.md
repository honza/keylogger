# keylogger analyzer

Give it keylogger data, and it will tell you what keyboard layout is best for
your particular typing habits


## Usage

Preparation:

* Install [logkeys](https://github.com/kernc/logkeys)
* Type on your computer as you usually do for a few days

Running the analyzer:

With lein:

* Clone this repository and `cd` into it
* `sudo cp /var/log/logkeys.log .`
* `lein run logkeys.log`

With a jar:

`java -jar <jar> logkeys.log`

Sample output:

```
qwerty: 38.69149%
colemak: 35.601215%
dvorak-programmer: 34.441067%
dvorak: 34.441067%
```


## License

GPLv3
