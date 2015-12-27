package com.group8.controllers;

/**
 * Created by Linus Eiderström Swahn
 *
 * Super class for all controllers.
 * Contains method and field for using the UI.
 */
public class BaseController
{

    protected MainScene mainScene;

    public void init(MainScene mainScene)
    {
        this.mainScene = mainScene;
    }
}
