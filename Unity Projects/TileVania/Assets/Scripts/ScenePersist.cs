using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ScenePersist : MonoBehaviour
{
    private static ScenePersist scenePersist;
    void Awake()
    {
        if (scenePersist)
        {
            Destroy(gameObject);
        }
        else
        {
            scenePersist = this;
            DontDestroyOnLoad(gameObject);
        }
    }
    // Start is called before the first frame update
    void Start()
    {
        
    }

    public static void Reset()
    {
        Destroy(scenePersist.gameObject);
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
