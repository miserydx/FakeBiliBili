package com.bilibili.model.event

/**
 * Created by miserydx on 17/12/9.
 */
open class BaseSwitchToolBarMenuEvent

class SwitchMainMenuEvent : BaseSwitchToolBarMenuEvent()

class SwitchRegionMenuEvent : BaseSwitchToolBarMenuEvent()

class ToggleDrawerEvent