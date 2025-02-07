package com.gocash.wallet.di.z17Singledi

class SinglediException(className: String): Exception("$className Instance requested before created. Be sure to use createInstance method on your main App class.")